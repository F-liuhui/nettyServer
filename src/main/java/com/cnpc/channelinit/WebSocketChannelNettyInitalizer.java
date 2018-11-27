package com.cnpc.channelinit;

import com.cnpc.handler.WebSocketChannelNettyHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketChannelNettyInitalizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline=socketChannel.pipeline();
        //编解码http消息(行，头，体)
        channelPipeline.addLast(new HttpServerCodec());
        //聚合消息
        channelPipeline.addLast(new HttpObjectAggregator(1024*1024));
        //处理webSocket close,ping,pong类型请求。
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/webSocket"));
        //自定义处理器。
        channelPipeline.addLast(new WebSocketChannelNettyHandler());
    }
}
