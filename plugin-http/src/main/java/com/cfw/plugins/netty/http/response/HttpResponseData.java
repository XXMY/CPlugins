package com.cfw.plugins.netty.http.response;

import io.netty.handler.codec.http.FullHttpRequest;

public class HttpResponseData {

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpResponseData{");
        sb.append("data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
