package com.cfw.plugins.netty.http.request;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public class HttpRequestData {
    // use this to find executor.
    private String path;
    private HttpMethod method;
    private ByteBuf data;
    private String contentType;
    private FullHttpRequest fullHttpRequest;

    public HttpRequestData(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public ByteBuf getData() {
        return data;
    }

    public void setData(ByteBuf data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public FullHttpRequest getFullHttpRequest() {
        return fullHttpRequest;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpRequestData{");
        sb.append("path='").append(path).append('\'');
        sb.append(", method=").append(method);
        sb.append(", data=").append(data);
        sb.append(", contentType='").append(contentType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
