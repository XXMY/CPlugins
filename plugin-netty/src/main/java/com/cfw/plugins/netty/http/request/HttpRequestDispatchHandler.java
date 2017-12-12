package com.cfw.plugins.netty.http.request;

import com.cfw.plugins.netty.http.mapping.ExecutorMapping;
import com.cfw.plugins.netty.http.mapping.executor.MappedExecutor;
import com.cfw.plugins.netty.http.response.HttpResponseData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dispatch request to executor.
 * @author CaiFangwei
 * @since 2017-11-6 12:24:41
 */
@ChannelHandler.Sharable
public class HttpRequestDispatchHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(HttpRequestDispatchHandler.class);

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
        this.logger.info("RequestData: {}",requestData);

        HttpResponseData responseData = new HttpResponseData(requestData.getFullHttpRequest());
        MappedExecutor executor = this.executorMapping.getExecutor(requestData.getPath());
        this.logger.info("MappedExecutor: {}",executor);

        // 400
        if(this.executorMapping == null || executor == null){
            responseData.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
            ctx.write(responseData);
            return ;
        }
        // 405
        if(requestData.getMethod() != executor.getHttpMethod()){
            responseData.setResponseStatus(HttpResponseStatus.METHOD_NOT_ALLOWED);
            ctx.write(responseData);
            return ;
        }

        try{
            Object result = executor.handle(requestData);
            responseData.setData(result);
            responseData.setResponseStatus(HttpResponseStatus.OK);
        }catch (Exception e){
            this.logger.error(e.getMessage(),e);
            responseData.setData(e.getMessage());
            responseData.setResponseStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }

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
