package com.cnpc.nettyserver;

import com.cnpc.channelinit.HttpChanneInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpNettyServer {
    public static void main(String[] args) {
        int port=8010;
        try {
            run(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void run(int port) throws Exception {
        //accept线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //io线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务管理者
            ServerBootstrap b = new ServerBootstrap();
            //绑定线程组
            b.group(bossGroup, workerGroup)
                    //绑定服务端线程类型
                    .channel(NioServerSocketChannel.class)
                    //io通道绑定handler
                    .childHandler(new HttpChanneInitializer())
                    //用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            //同步绑定监听端口
            ChannelFuture ch = b.bind(port).sync();
            System.out.println("http server started at  port " + port);
            System.out.println("open you browser and navigate to  http://localhost:" + port + "/");
            //同步监听关闭
            ch.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
