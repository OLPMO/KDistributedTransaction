package com.kdt.server.command;

import com.kdt.common.Command;
import com.kdt.common.KTransactionMessage;
import com.kdt.common.TransactionState;
import com.kdt.server.pojo.TxGroupInfo;
import com.kdt.server.util.SendCommandUtil;
import com.kdt.server.util.TransactionInfoManager;

public class CommitStrategy implements CommandStrategy {

    @Override
    public void execute(KTransactionMessage txMessage, TransactionInfoManager txInfoManager) {
        String groupID = txMessage.getGroupID();
        TxGroupInfo txGroupInfo = txInfoManager.getTxGroupInfoByGroupID(groupID);
        if(TransactionState.ROLLBACK != txGroupInfo.getTxGroupState()){
            txGroupInfo.setTxGroupState(TransactionState.COMMIT);
        }

        txGroupInfo.getCntOfSubTxConfirmedState().incrementAndGet();

        SendCommandUtil.checkAndSendCommand(txGroupInfo);

    }
}
