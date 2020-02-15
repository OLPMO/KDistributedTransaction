package com.kdt.client.aspect;

import com.kdt.client.annotation.KDistributedTransaction;
import com.kdt.client.tx.KTransaction;
import com.kdt.client.util.NettyClient;
import com.kdt.client.util.TxManager;
import com.kdt.common.Command;
import com.kdt.common.KTransactionMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class KDistributedTransactionAspect implements Ordered {

    @Around("@annotation(kdt)")
    public Object around(ProceedingJoinPoint pjp, KDistributedTransaction kdt)  {
        Object result = null;
        //before
        System.out.println("KDistributedTransactionAspect enter");
        KTransaction tx = new KTransaction();
        TxManager.setCurKTransaction(tx);

        String groupID = TxManager.getOrCreateGroupID();
        tx.setGroupID(groupID);

        String newTxID = TxManager.createTxID();
        tx.setId(newTxID);

        TxManager.setTxViaID(tx.getId(), tx);

        KTransactionMessage txMessage = new KTransactionMessage();
        txMessage.setGroupID(groupID);
        txMessage.setTransactionID(newTxID);
        txMessage.setFinalTx(kdt.isFinalTx());
        txMessage.setCommand(Command.REGISTE);

        NettyClient nettyClient = new NettyClient();
        tx.setNettyClient(nettyClient);

        nettyClient.sendMsg(JSON.toJSONString(txMessage));
        try {
            result = pjp.proceed();
            txMessage.setCommand(Command.COMMIT);
        } catch (Throwable throwable) {
            txMessage.setCommand(Command.ROLLBACK);
            System.out.println("groupID:"+groupID+"->newTxID："+newTxID+" exe failed");
//            throwable.printStackTrace();
        } finally {
            nettyClient.sendMsg(JSON.toJSONString(txMessage));
            return result;
        }

    }

    //数值小的先执行
    //先执行Spring的@Transactional注解
    @Override
    public int getOrder(){
        return 10000;
    }
}
