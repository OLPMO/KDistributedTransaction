package com.kdt.server.util;

import com.alibaba.fastjson.JSON;
import com.kdt.common.Command;
import com.kdt.common.KTransactionMessage;
import com.kdt.server.pojo.TxGroupInfo;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SendCommandUtil {
    public static boolean isStateOfTxGroupConfirmed(TxGroupInfo txGroupInfo){
        boolean isStateConfirmed = txGroupInfo.isFinalSubTxRegistered();
        if (isStateConfirmed){
            synchronized (txGroupInfo){
                isStateConfirmed = txGroupInfo.isFinalSubTxRegistered() && (txGroupInfo.getNumOfSubTx().get() == txGroupInfo.getCntOfSubTxConfirmedState().get());
            }
        }

        return isStateConfirmed;
    }

    public static void checkAndSendCommand(TxGroupInfo txGroupInfo){
        if (isStateOfTxGroupConfirmed(txGroupInfo)){
            System.out.println("checkAndSendCommand -> NumOfSubTx:" + txGroupInfo.getNumOfSubTx().toString() + ",CntOfSubTxConfirmedState:" +txGroupInfo.getCntOfSubTxConfirmedState().toString() );
            sendFinalCommand(txGroupInfo);
        }
    }

    //netty的io操作大部分是异步操作因此不考虑返回值
    public static void sendFinalCommand(TxGroupInfo txGroupInfo){
        ConcurrentHashMap<String, ChannelHandlerContext> ctxMap = txGroupInfo.getMapOfChannelContext();
        final String groupID = txGroupInfo.getGroupID();
        for(Map.Entry<String, ChannelHandlerContext> entry: ctxMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            ChannelHandlerContext ctx = entry.getValue();
            String txID = entry.getKey();
            Command cmd = TxStateUtil.TxState2TxCommand(txGroupInfo.getTxGroupState());
            KTransactionMessage txMsg = new KTransactionMessage(cmd, groupID, txID, false);
            String msg = JSON.toJSONString(txMsg);

//            ChannelFuture future = ctx.channel().writeAndFlush(msg);
            ChannelFuture future = ctx.writeAndFlush(msg);
//            ctx.flush();
            future.addListener((ChannelFutureListener) callBackFuture -> {
                // Perform post-closure operation
                // ...
                if(!callBackFuture.isDone() || !callBackFuture.isSuccess()){
                    //此处置仅用于demo
                    System.out.println("[ERROR]:sendFinalCommand Failed");
                }
                System.out.println("sendFinalCommand close connect!");
                //指令发送完毕后主动关闭连接
                ctx.close();
            });
        }
    }
}
