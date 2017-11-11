package com.cfw.plugins.netty.http.request;

import com.cfw.plugins.netty.http.mapping.ExecutorMapping;
import com.cfw.plugins.netty.http.response.HttpResponseData;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

/**
 * Dispatch request to executor.
 * @author CaiFangwei
 * @since 2017-11-6 12:24:41
 */
public class HttpRequestDispatchHandler extends ChannelInboundHandlerAdapter {

    private ExecutorMapping executorMapping;

    public HttpRequestDispatchHandler(){}

    public HttpRequestDispatchHandler(ExecutorMapping executorMapping) {
        this.executorMapping = executorMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (!(msg instanceof HttpRequestData))
            return ;
        HttpRequestData requestData = (HttpRequestData) msg;
        System.out.println("*************************RequestData: " + requestData);

        HttpResponseData responseData = new HttpResponseData(requestData.getFullHttpRequest());
        responseData.setData("SUCCESS");

        ctx.write(responseData);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
