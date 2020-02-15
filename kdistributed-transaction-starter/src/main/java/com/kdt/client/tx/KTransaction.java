package com.kdt.client.tx;

import com.kdt.client.util.KTask;
import com.kdt.client.util.NettyClient;
import com.kdt.common.TransactionState;
import lombok.Data;

@Data
public class KTransaction {
    private KTask task;
    private String id;
    private TransactionState state;
    private String groupID;
    private NettyClient nettyClient;
    private volatile boolean isInProcessOfKDistTx;

    public KTransaction() {
        this.task = new KTask();
        isInProcessOfKDistTx = true;
    }
}
