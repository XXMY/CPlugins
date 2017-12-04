package com.cfw.plugins.netty.http.mapping.executor;

import com.cfw.plugins.netty.http.HttpMethod;
import com.cfw.plugins.netty.http.request.HttpRequestData;

public interface MappedExecutor {

    Object handle(Object... parameters) throws Exception;
    Object handle(HttpRequestData requestData) throws Exception;

    HttpMethod getHttpMethod();
}
