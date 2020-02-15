package com.kdt.server.util;

import com.kdt.common.Command;
import com.kdt.common.TransactionState;

public class TxStateUtil {
    static public Command TxState2TxCommand(TransactionState state){
        Command cmd = Command.COMMIT;
        if(state == TransactionState.ROLLBACK){
            cmd = Command.ROLLBACK;
        }
        return cmd;
    }
}
