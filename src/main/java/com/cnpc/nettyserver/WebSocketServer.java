package com.cnpc.nettyserver;

import com.cnpc.handler.WebSocketServerHandler;
import com.cnpc.listener.MyFutureListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 基于netty 的websocket 服务实现
 *
 */
public class WebSocketServer {

    public static void main(String[] args) {
        int port=8009;
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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //匿名内部类实现
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //得到管道
                            ChannelPipeline pipeline = ch.pipeline();
                            //用于Http请求的编码或者解码
                            pipeline.addLast("http-codec", new HttpServerCodec());
                            //把Http消息组成完整地HTTP消息，大小为65536
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                            //向客户端发送HTML5文件
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            //实际处理的Handler
                            pipeline.addLast("handler", new WebSocketServerHandler());
                    }
            });
            //同步绑定监听端口
            ChannelFuture ch = b.bind(port).sync();
            System.out.println("web socket server started at  port " + port);
            System.out.println("open you browser and navigate to  http://localhost:" + port + "/");
            //同步监听关闭
            ch.channel().closeFuture().sync().addListener(new MyFutureListener());

        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
