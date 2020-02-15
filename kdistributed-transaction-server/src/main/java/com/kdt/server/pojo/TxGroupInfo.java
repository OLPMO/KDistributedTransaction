package com.kdt.server.pojo;

import com.kdt.common.TransactionState;
import io.netty.channel.ChannelHandlerContext;
import lombok.EqualsAndHashCode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode
public class TxGroupInfo {
    private final String groupID;
    private AtomicInteger numOfSubTx;
    private TransactionState txGroupState;
    private AtomicInteger cntOfSubTxConfirmedState;
    private volatile boolean isFinalSubTxRegistered;
    private final ConcurrentHashMap<String, ChannelHandlerContext> mapOfChannelContext = new ConcurrentHashMap<>();

    public TxGroupInfo(String groupID) {
        this.groupID = groupID;
        this.numOfSubTx = new AtomicInteger(0);
        this.cntOfSubTxConfirmedState = new AtomicInteger(0);
        this.isFinalSubTxRegistered = false;
    }

    public String getGroupID() {
        return this.groupID;
    }

    public AtomicInteger getNumOfSubTx() {
        return this.numOfSubTx;
    }

    public TransactionState getTxGroupState() {
        return this.txGroupState;
    }

    public AtomicInteger getCntOfSubTxConfirmedState() {
        return this.cntOfSubTxConfirmedState;
    }

    public boolean isFinalSubTxRegistered() {
        return this.isFinalSubTxRegistered;
    }

    public void setIsFinalSubTxRegistered(boolean isRegistered) {
        this.isFinalSubTxRegistered = isRegistered;
    }

    public void setTxGroupState(TransactionState txGroupState) {
        this.txGroupState = txGroupState;
    }

    public void setChannelContextBySubTxID(String subTxID, ChannelHandlerContext ctx){
        this.mapOfChannelContext.put(subTxID, ctx);
    }

    public ConcurrentHashMap<String, ChannelHandlerContext> getMapOfChannelContext(){
        return this.mapOfChannelContext;
    }

    public String toString() {
        return "TxGroupInfo(groupID=" + this.getGroupID() + ", numOfSubTx=" + this.getNumOfSubTx() + ", txGroupState=" + this.getTxGroupState() + ", cntOfSubTxComfirmedState=" + this.getCntOfSubTxConfirmedState() + ", isNumOfSubTxBeenComfirmed=" + this.isFinalSubTxRegistered() + ")";
    }
}
