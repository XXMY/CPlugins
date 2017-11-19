package com.cfw.plugins.netty.http.response;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpResponseData {

    private HttpResponseStatus responseStatus;

    private Object data;

    private FullHttpRequest fullHttpRequest;

    public HttpResponseData(FullHttpRequest fullHttpRequest){
        this.fullHttpRequest = fullHttpRequest;
    }

    public Object getData() {
        return data;
    }

    public FullHttpRequest getFullHttpRequest() {
        return fullHttpRequest;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(HttpResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setFullHttpRequest(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpResponseData{");
        sb.append("responseStatus=").append(responseStatus);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
