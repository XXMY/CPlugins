package com.cfw.plugins.netty.http.response;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class HttpResponseHandler extends ChannelOutboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(HttpResponseHandler.class);

    /**
     * Calls {@link ChannelHandlerContext#write(Object, ChannelPromise)} to forward
     * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param msg
     * @param promise
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(!(msg instanceof HttpResponseData))
            return ;
        HttpResponseData responseData = (HttpResponseData) msg;
        this.logger.info("ResponseData: {}", responseData);

        if (HttpUtil.is100ContinueExpected(responseData.getFullHttpRequest())) {
            ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
        }

        boolean keepAlive = HttpUtil.isKeepAlive(responseData.getFullHttpRequest());
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                responseData.getResponseStatus(),
                Unpooled.wrappedBuffer(JSON.toJSONString(responseData.getData()).getBytes()));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(response);
        }
    }


}
