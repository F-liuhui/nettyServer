package com.cnpc.nettyserver;

import com.cnpc.channelinit.WebSocketChannelNettyInitalizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * WebSocketNettyServer简单实现
 */
public class WebSocketNettyServer {

    public static void main(String[] args) {
        int port=8011;
        start(port);
    }
    public static void start(int port){
        EventLoopGroup boosGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        ServerBootstrap server=new ServerBootstrap();

        try {
            server.group(boosGroup);
            server.group(workGroup);
            server.channel(NioServerSocketChannel.class);
            server.childHandler(new WebSocketChannelNettyInitalizer());
            server.option(ChannelOption.SO_BACKLOG,1024);
            server.childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture channel = server.bind(port).sync();
            System.out.println("open you browser and navigate to  http://localhost:" + port + "/");
            channel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
