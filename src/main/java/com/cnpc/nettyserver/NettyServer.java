package com.cnpc.nettyserver;

import com.cnpc.handler.SimpleChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty 服务端练习
 */
public class NettyServer {
    private static int port=8008;
    public static void main(String[] args) {

        initNettyServer();
    }
    public static  void initNettyServer() {
        //接受连接的线程组(主线程组)
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        //io操作的线程组
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            //服务启动和管理者
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            //绑定主线程组
            serverBootstrap.group(bossGroup,workGroup);
            //指定服务端通道类型
            serverBootstrap.channel(NioServerSocketChannel.class);
            //参数设置，指定队列大小。
            serverBootstrap.option(ChannelOption.SO_BACKLOG,128);
            //为子通道绑定handler
            serverBootstrap.childHandler(new SimpleChannelInitializer());
            //服务器绑定端口监听（同步监听）
            ChannelFuture ChannelFuture = serverBootstrap.bind(port).sync();
            //监听服务器关闭，执行该方法会阻塞知道异步线程完成。
            ChannelFuture.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //释放资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
