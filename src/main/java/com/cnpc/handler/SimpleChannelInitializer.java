package com.cnpc.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 * 简单的通道handler
 *
 */
public class SimpleChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //得到一个通道
        ChannelPipeline pipeline= channel.pipeline();

        //帧解码器 Delimiters.lineDelimiter()表示以\n结尾表示一个完整的消息，8192表示流长度。
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        //字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        //自己的逻辑Handler
        pipeline.addLast("handler", new SimpleServerHandler());
    }
}
