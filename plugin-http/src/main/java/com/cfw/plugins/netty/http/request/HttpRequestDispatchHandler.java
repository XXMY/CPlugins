package com.cfw.plugins.netty.http.request;

import com.cfw.plugins.netty.http.mapping.ExecutorMapping;
import com.cfw.plugins.netty.http.mapping.MappedExecutor;
import com.cfw.plugins.netty.http.response.HttpResponseData;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Dispatch request to executor.
 * @author CaiFangwei
 * @since 2017-11-6 12:24:41
 */
@Component
@ChannelHandler.Sharable
public class HttpRequestDispatchHandler extends ChannelInboundHandlerAdapter {

    private ExecutorMapping executorMapping;

    public HttpRequestDispatchHandler(){}

    @Autowired
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
        MappedExecutor executor = this.executorMapping.getExecutor(requestData.getPath());
        if(this.executorMapping == null || executor == null){
            responseData.setData("Could not found correspond controller.");
            ctx.write(responseData);
            return ;
        }

        Object result = executor.execute(requestData);
        if(result !=null)
            responseData.setData(result);

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
