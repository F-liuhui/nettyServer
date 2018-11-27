package com.cnpc.handler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import java.util.Date;

public class WebSocketChannelNettyHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        if(msg!=null){
            if(msg instanceof WebSocketFrame){
                if(msg instanceof BinaryWebSocketFrame){
                    BinaryWebSocketFrame binaryWebSocketFrame= (BinaryWebSocketFrame) msg;
                    manageBinaryWebSocketFrame(channelHandlerContext,binaryWebSocketFrame);
                }else if(msg instanceof ContinuationWebSocketFrame){
                    ContinuationWebSocketFrame continuationWebSocketFrame= (ContinuationWebSocketFrame) msg;
                    manageContinuationWebSocketFrame(channelHandlerContext,continuationWebSocketFrame);
                }else if(msg instanceof TextWebSocketFrame){
                     TextWebSocketFrame textWebSocketFrame= (TextWebSocketFrame) msg;
                     manageTextWebSocketFrame(channelHandlerContext,textWebSocketFrame);
                }
            }else if (msg instanceof HttpRequest){
                FullHttpRequest fullHttpRequest= (FullHttpRequest) msg;
            }
        }
    }

    private void manageBinaryWebSocketFrame(ChannelHandlerContext centent,BinaryWebSocketFrame msg){
        if(msg!=null){
            boolean b=msg.isFinalFragment();
            if(b){
               String message=msg.content().toString();
               if(message!=null && !message.equals("")){
                   System.out.println(centent.channel().remoteAddress().toString()+"发送的消息是"+message);
                   centent.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.copiedBuffer(("欢饮使用netty服务，现在时刻"+new Date().toString()).getBytes())));
               }
            }
        }

    }

    private void manageContinuationWebSocketFrame(ChannelHandlerContext centent,ContinuationWebSocketFrame msg){
        if(msg!=null){
            boolean b=msg.isFinalFragment();
            if(b){
                String messege=msg.content().toString();
                System.out.println(centent.channel().remoteAddress().toString()+"发送的消息是"+messege);
                centent.channel().writeAndFlush(new TextWebSocketFrame("欢饮使用netty服务，现在时刻"+new Date().toString()));
            }
        }
    }
    private  void manageTextWebSocketFrame(ChannelHandlerContext centent,TextWebSocketFrame msg){
        if(msg!=null){
            boolean b=msg.isFinalFragment();
            if(b){
                String messsage=msg.text();
                System.out.println(centent.channel().remoteAddress().toString()+"发送的消息是"+messsage);
                centent.channel().writeAndFlush(new TextWebSocketFrame("欢饮使用netty服务，现在时刻"+new Date().toString()));
            }
        }
    }
}
