package com.kdt.client.netty.handler;

import com.kdt.client.tx.KTransaction;
import com.kdt.client.util.TxManager;
import com.kdt.common.KTransactionMessage;
import com.kdt.common.TransactionState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import com.alibaba.fastjson.JSON;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //netty 的操作是异步操作，和主流程的线程不是同一个线程，因此不能用threadlocal里面的tx
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        String strMsg = (String) msg;
		System.out.println("Client received: " + strMsg);
        KTransactionMessage txMessage = JSON.parseObject(strMsg, KTransactionMessage.class);
        KTransaction tx = TxManager.getTxViaID(txMessage.getTransactionID());
        TransactionState state = TxManager.txCommand2TxState(txMessage.getCommand());
        tx.setState(state);
        //唤醒commit线程
        tx.getTask().signalTask();

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
