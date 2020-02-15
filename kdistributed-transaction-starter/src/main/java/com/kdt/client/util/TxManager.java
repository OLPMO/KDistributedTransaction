package com.kdt.client.util;

import com.kdt.client.tx.KTransaction;
import com.kdt.common.Command;
import com.kdt.common.TransactionState;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TxManager {
    private static ThreadLocal<KTransaction> thrdLocalOfKTx = new ThreadLocal<>();
    private static ThreadLocal<String> thrdLocalOfGroupID = new ThreadLocal<>();
    private static ConcurrentHashMap<String, KTransaction> mapOfTxID2Tx = new ConcurrentHashMap<>();


    public static String getOrCreateGroupID(){
        String groupID = thrdLocalOfGroupID.get();
        if(groupID == null){
            groupID = UUID.randomUUID().toString();
            thrdLocalOfGroupID.set(groupID);
        }
        return groupID;
    }

    public static void setGroupID(String groupID){
        thrdLocalOfGroupID.set(groupID);
    }

    public static String createTxID(){
        return UUID.randomUUID().toString();
    }

    public static KTransaction getCurKTransaction(){
        return thrdLocalOfKTx.get();
    }

    //是否处于分布式事务的处理过程
    public static boolean isInProcessOfKDistTx(KTransaction tx){
        System.out.println("isInProcessOfKDistTx:"+(tx != null && tx.isInProcessOfKDistTx()));
        return tx != null && tx.isInProcessOfKDistTx();
    }

    public static void setCurKTransaction(KTransaction tx){
        thrdLocalOfKTx.set(tx);
    }

    public static void setTxViaID(String txID, KTransaction tx){
        mapOfTxID2Tx.put(txID, tx);
    }

    public static KTransaction getTxViaID(String txID){
        return mapOfTxID2Tx.get(txID);
    }

    public static void RemoveTxViaID(String txID){
        mapOfTxID2Tx.remove(txID);
    }

    public static TransactionState txCommand2TxState(Command cmd){
        TransactionState state = TransactionState.COMMIT;
        if(cmd == Command.ROLLBACK){
            state = TransactionState.ROLLBACK;
        }
        return state;
    }
}
