package com.kdt.server.util;

import com.kdt.server.pojo.TxGroupInfo;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

public class TransactionInfoManager {
    private final static ConcurrentHashMap<String, TxGroupInfo> mapOfID2TxGroupInfo = new ConcurrentHashMap<>();

    private ThreadLocal<ChannelHandlerContext> curChannelHandlerContext = new ThreadLocal<>();

    public ChannelHandlerContext getCurChannelHandlerContext(){
        return this.curChannelHandlerContext.get();
    }

    public void setCurChannelHandlerContext(ChannelHandlerContext curCtx) {
        this.curChannelHandlerContext.set(curCtx);
    }

    public TxGroupInfo getTxGroupInfoByGroupID(String groupID){
        return mapOfID2TxGroupInfo.get(groupID);
    }

    public TxGroupInfo setTxGroupInfoByGroupID(String groupID, TxGroupInfo txGroupInfo){
        return mapOfID2TxGroupInfo.putIfAbsent(groupID, txGroupInfo);
    }
}
