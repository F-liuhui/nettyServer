package com.cnpc.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import java.util.HashMap;
import java.util.Map;

public class HttpChannelHandler extends SimpleChannelInboundHandler<Object> {

    private String content = "hello world + oooooooooooooooooooooooooooooooooooonnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";

    //模拟几个路径
    private final static String LOC = "302";
    private final static String NOT_FOND = "404";
    private final static String BAD_REQUEST = "400";
    private final static String INTERNAL_SERVER_ERROR = "500";

    private static Map<String, HttpResponseStatus> mapStatus = new HashMap<String, HttpResponseStatus>();

    static {
        mapStatus.put(LOC, HttpResponseStatus.FOUND);
        mapStatus.put(NOT_FOND, HttpResponseStatus.NOT_FOUND);
        mapStatus.put(BAD_REQUEST, HttpResponseStatus.BAD_REQUEST);
        mapStatus.put(INTERNAL_SERVER_ERROR, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 实现简单的Http服务
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(o instanceof HttpRequest){
            FullHttpRequest req = (FullHttpRequest) o;
            boolean keepALive = HttpUtil.isKeepAlive(req);
            System.out.println("method="+req.method());
            System.out.println("uri="+req.uri());
            String uri=req.uri().replace("/","").trim();
            FullHttpResponse fullHttpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
            if(mapStatus.get(uri)!=null){
                fullHttpResponse.setStatus(mapStatus.get(uri));
                fullHttpResponse.content().writeBytes(mapStatus.get(uri).toString().getBytes());
            }else{
               fullHttpResponse.content().writeBytes(content.getBytes());
            }
            //重定向
            if(fullHttpResponse.status().equals(HttpResponseStatus.FOUND)){
                //重定向消息设置在head里
               fullHttpResponse.headers().set(HttpHeaderNames.LOCATION,"https://www.baidu.com/");
            }
            //设置消息头，响应内容类型
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
            //设置消息头，响应体数据长度。（byte长度）;
            fullHttpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
            if (keepALive) {
                //设置消息头，KEEP_ALIVE，保持长连接。
                fullHttpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                //将消息头写出去
                channelHandlerContext.writeAndFlush(fullHttpResponse);
            } else {
                //否则发送完数据后关闭连接管道
                channelHandlerContext.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
