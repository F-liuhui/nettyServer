package com.cnpc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.BAD_REQUEST;

/**
 *
 *
 * 推荐继承SimpleChannelInboundHandler<Object>而不是 ChannelInboundHandlerAdapter，因为后者已经继承了前者。
 *
 */

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;

    /**
     *
     * 接受消息时调用（准备读取消息时）
     *
     * @param msg
     * @return
     * @throws Exception
     */
    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        System.out.println("this is acceptInboundMessage ()");
        return super.acceptInboundMessage(msg);
    }

    /**
     * channel 注册到一个EventLoop上后调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is channelRegistered ()");
        super.channelRegistered(ctx);
    }

    /**
     * channel已创建但未注册到一个EventLoop后调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is channelUnregistered ()");
        super.channelUnregistered(ctx);
    }

    /*
     * channel被启用的时候触发（在建立连接的时候）
     * channel 变为活跃状态(连接到了远程主机)，现在可以接收和发送数据了
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is channelActive ()");
        super.channelActive(ctx);
    }

    /**
     * 当通道离开活动状态并不再连接到其远程对等点时调用,既channel 处于非活跃状态，没有连接到远程主机。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is channelInactive ()");
        super.channelInactive(ctx);
    }


    /**
     * 当用户调用channel . fireusereventtrigger(…)来通过ChannelPipeline传递pojo时，被触发。
     * 这可以用来通过ChannelPipeline传递特定于用户的事件，从而允许处理这些事件。
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("this is userEventTriggered ()");
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 当通道的可写状态更改时调用。用户可以确保写操作不会太快(有OutOfMemoryError错误的风险)，
     * 也可以在通道再次可写时恢复写操作。可写性阈值可以通过Channel.config().setwritehighwatermark()
     * 和Channel.config(). setwritelowwatermark()来设置。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is channelWritabilityChanged ()");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    protected void ensureNotSharable() {
        System.out.println("this is ensureNotSharable ()");
        super.ensureNotSharable();
    }

    /**
     *(连接刚过来时调用，可在里面设置是否拒绝连接)
     * @return
     */
    @Override
    public boolean isSharable() {
        System.out.println("this is isSharable ()");
        return super.isSharable();
    }

    /*
     * (non-Javadoc)
     * 每当从服务端收到新的客户端通道连接时
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is handlerAdded ()");
        super.handlerAdded(ctx);
    }

    /*
     * (non-Javadoc)
     * 每当从服务端收到客户端断开时
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is handlerRemoved ()");
        super.handlerRemoved(ctx);
    }

    /*
     * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，
     * 即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。
     * 在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
     * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，
     * 比如你可能想在关闭连接之前发送一个错误码的响应消息。
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("this is exceptionCaught ()");
        cause.printStackTrace();
        ctx.close();
    }



    /*@Override 现有版本中没有 messageReceived 方法 netty5.x 版本中存在该方法，代替了channelRead0方法
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
    }*/

    /**
     * 服务端读取通道数据完成后会执行该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is channelReadComplete ()");
        ctx.flush();
    }



    /**
     *  每当服务端读客户端写入信息时会执行该方法,
     * (在channelReadComplete 方法前执行，但是如果读取的数据中没有结束标记\n 的话不会走该方法，
     * 但是channelReadComplete方法任然会执行)
     * 其中如果你使用的是 Netty 5.x 版本时，
     * 需要把 channelRead0() 重命名为messageReceived()
     *
     * 需要注意的是：该方法不像channelRead()需要手动释放资源，该方法不用手动释放资源，由服务器 自己释放，推荐使用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("this is channelRead0 ()");
        // 传统的http接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // webSocket方式接入
        else if (msg instanceof WebSocketFrame) {
            handleWebSockerFrame(ctx, (WebSocketFrame) msg);
        }

    }
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        //http码返回失败
        //getDecoderResult()被启用了
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSockerFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //判断是否是ping 消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //本例仅支持文本,不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        //返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        ctx.channel().write(new TextWebSocketFrame(request + ",欢饮使用Netty WebSocket服务，现在时刻:" + new java.util.Date().toString()));

    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        //返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        //如果是非keep-Alive，关闭连接
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
