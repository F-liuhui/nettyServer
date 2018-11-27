package com.cnpc.channelinit;

import com.cnpc.handler.HttpChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;


public class HttpChanneInitializer extends ChannelInitializer<SocketChannel> {
    //ssl加密数据
    //private SslContext sslContext = SslContextBuilder.forServer(new File(""),new File("")).build();
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChunkedWriteHandler ChunkedWriteHandler =null;
        /*加密引擎
        SSLEngine sslEngine=sslContext.newEngine(socketChannel.alloc());*/
        ChannelPipeline channelPipeline=socketChannel.pipeline();
        // netty服务端用于解编码Http和websocket消息
        channelPipeline.addLast("codec",new HttpServerCodec());
        // 服务端压缩数据，在解码之后的第一个添加。
        channelPipeline.addLast("compressor",new HttpContentCompressor());
        //聚合httpRequest消息，指定大小。
        channelPipeline.addLast("agGeGator",new HttpObjectAggregator(1024*1024));
        //自定义handler
        channelPipeline.addLast("handler",new HttpChannelHandler());
    }
}
