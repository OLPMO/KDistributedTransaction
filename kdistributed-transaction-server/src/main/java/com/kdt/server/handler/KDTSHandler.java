package com.kdt.server.handler;

import com.alibaba.fastjson.JSON;
import com.kdt.common.Command;
import com.kdt.common.KTransactionMessage;
import com.kdt.server.command.CommandStrategy;
import com.kdt.server.command.CommandStrategyFactory;
import com.kdt.server.util.TransactionInfoManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class KDTSHandler extends ChannelInboundHandlerAdapter {

    private static TransactionInfoManager txInfoManager = new TransactionInfoManager();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " active !");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        txInfoManager.setCurChannelHandlerContext(ctx);

        String strMsg = (String)msg;
		System.out.println("Server received: " + strMsg);
        KTransactionMessage transactionMessage = JSON.parseObject(strMsg, KTransactionMessage.class);
        procMsgViaCommand(transactionMessage);
        //		ctx.write(in);
//        ctx.write(msg);
//        ctx.flush();
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

    private void procMsgViaCommand(KTransactionMessage transactionMessage) {
        Command command = transactionMessage.getCommand();
        CommandStrategyFactory strategyFactory = new CommandStrategyFactory();
        CommandStrategy commandStrategy = strategyFactory.getStrategy(command);

        commandStrategy.execute(transactionMessage, txInfoManager);
    }
}
