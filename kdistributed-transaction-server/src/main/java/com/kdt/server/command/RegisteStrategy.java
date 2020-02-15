package com.kdt.server.command;

import com.kdt.common.KTransactionMessage;
import com.kdt.server.pojo.TxGroupInfo;
import com.kdt.server.util.TransactionInfoManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

public class RegisteStrategy implements CommandStrategy {

    @Override
    public void execute(KTransactionMessage transactionMessage, TransactionInfoManager txInfoManager) {
        ChannelHandlerContext ctx = txInfoManager.getCurChannelHandlerContext();

        String groupID = transactionMessage.getGroupID();
        String transactionID = transactionMessage.getTransactionID();

        TxGroupInfo txGroupInfo = txInfoManager.getTxGroupInfoByGroupID(groupID);

        if(null == txGroupInfo){
            synchronized (txInfoManager){
                txGroupInfo = txInfoManager.getTxGroupInfoByGroupID(groupID);
                if (txGroupInfo == null) {
                    System.out.println("setChannelContextMap set!");
                    txGroupInfo = new TxGroupInfo(groupID);
                    txInfoManager.setTxGroupInfoByGroupID(groupID, txGroupInfo);
                }
            }
        }
        txGroupInfo.getNumOfSubTx().incrementAndGet();
        txGroupInfo.setChannelContextBySubTxID(transactionID, ctx);
        if(!txGroupInfo.isFinalSubTxRegistered()){
            txGroupInfo.setIsFinalSubTxRegistered(transactionMessage.isFinalTx());
        }

        txInfoManager.setCurChannelHandlerContext(null);
    }
}
