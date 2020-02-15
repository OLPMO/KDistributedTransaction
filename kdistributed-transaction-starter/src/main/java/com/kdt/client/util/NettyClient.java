package com.kdt.client.util;

import com.kdt.client.netty.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.FutureListener;

import java.net.InetSocketAddress;


public class NettyClient {
    private EventLoopGroup eventLoopGroup;
    private Channel channel;
    private Bootstrap bootStrap;
    ChannelFuture connectFuture;
//    private NettyClientHandler clientHandler;

    public NettyClient(){
//        clientHandler = new NettyClientHandler();
        init();
        start();
    }

    public void init(){
        // 服务类
        bootStrap = new Bootstrap();
        eventLoopGroup= new NioEventLoopGroup();
        // 设置管道工厂
        bootStrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class) // (3)
                .handler(new ChannelInitializer<NioSocketChannel>() { // (4)

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // TODO Auto-generated method stub
                        //解码
                        //编码
                        ch.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

                        ch.pipeline().addLast(new NettyClientHandler());


                    }
                });


    }

    public void start() {
        // 连接服务器
        connectFuture = bootStrap.connect(new InetSocketAddress("127.0.0.1", 8090));
        channel = connectFuture.channel();

        System.out.println("连接远程服务器 8090 端口成功，你现在可以开始发消息了。");
    }

    public boolean isServerConnected(){
        return connectFuture.isDone() && connectFuture.isSuccess();
    }

    public void sendMsg(String msg){
        System.out.println("Client sent msg:" + msg);
        ChannelFuture sendFuture = channel.writeAndFlush(msg);
//        channel.flush();
        // ...
        sendFuture.addListener((ChannelFutureListener) callBackFuture -> {
            // Perform post-closure operation
            // ...
            if(!callBackFuture.isDone() || !callBackFuture.isSuccess()){
                //此处置仅用于demo
                System.out.println("[CLIENT-ERROR]:sendMsg Failed:");

            }

        });
    }

    public void close() {
        connectFuture.awaitUninterruptibly().addListener((ChannelFutureListener) callBackFuture -> {
            // Perform post-closure operation
            // ...
            if(!callBackFuture.isDone() || !callBackFuture.isSuccess()){
                //此处置仅用于demo
                System.out.println("[ERROR]:sendCommand Failed");
            }
            channel.close();
            eventLoopGroup.shutdownGracefully().sync();
        });
    }
}
