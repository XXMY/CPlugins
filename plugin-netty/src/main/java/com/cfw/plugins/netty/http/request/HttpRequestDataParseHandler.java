package com.cfw.plugins.netty.http.request;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.http.HttpHeaders;

@ChannelHandler.Sharable
public class HttpRequestDataParseHandler extends ChannelInboundHandlerAdapter{

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof HttpRequest))
            return;
        FullHttpRequest request = (FullHttpRequest) msg;
        HttpRequestData requestData = this.getRequestData(request);

        ctx.fireChannelRead(requestData);
    }

    private HttpRequestData getRequestData(FullHttpRequest request){
        HttpRequestData requestData = new HttpRequestData(request);
        requestData.setMethod(request.method());
        requestData.setContentType(request.headers().get(HttpHeaders.CONTENT_TYPE));

        String uri = request.uri();
        if(HttpMethod.POST.equals(request.method())
                || HttpMethod.PUT.equals(request.method())
                || HttpMethod.DELETE.equals(request.method())){
            requestData.setPath(uri);
            requestData.setData(request.content());
        }else if(HttpMethod.GET.equals(request.method())){
            String uriSplittedArray [] = uri.split("\\?");
            requestData.setPath(uriSplittedArray[0]);
            if(uriSplittedArray.length == 2)
                requestData.setData(Unpooled.copiedBuffer(uriSplittedArray[1].getBytes()));
        }

        return requestData;
    }
}
