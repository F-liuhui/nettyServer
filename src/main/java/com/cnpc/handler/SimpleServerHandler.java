package com.cnpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

/**
 *
 * 通道handler 主要用于具体的逻辑和编码解码器的绑定
 *
 */
public class SimpleServerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 客户端可服务端建立连接时触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // System.out.println("server:客户端地址是 : " + ctx.channel().remoteAddress() + " 正在连接。。。 !");
        System.out.println("this is channelActive ()");
        //返回客户端信息
        ctx.writeAndFlush( "server:欢迎访问我 " + InetAddress.getLocalHost().getHostName() + " 服务!\n");
        //在调用父类方法
        super.channelActive(ctx);
    }

    /**
     *
     * 读取客户端消息时触发该方法
     * @param
     * @param msg
     * @throws Exception
     */
   /** @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

        // 返回客户端消息
        ctx.writeAndFlush("Received your message !\n");
    }*/

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

    @Override
    public boolean isSharable() {
        System.out.println("this is isSharable ()");
        return super.isSharable();
    }

    /**
     * (non-Javadoc)
     * 每当从服务端收到新的客户端连接时
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is handlerAdded ()");
        super.handlerAdded(ctx);
    }

    /**
     * (non-Javadoc)
     * 每当从服务端收到客户端断开时
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("this is handlerRemoved ()");
        super.handlerRemoved(ctx);
    }

    /**
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
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg.toString());

        // 返回客户端消息
        ctx.writeAndFlush("Received your message !\n");

    }
    /**@Override 现有版本中没有 messageReceived 方法 netty5.x 版本中存在该方法，代替了channelRead0方法
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

}
