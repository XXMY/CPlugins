package com.cfw.plugins.netty.http.mapping;

import com.cfw.plugins.netty.http.HttpMethod;
import com.cfw.plugins.netty.http.request.HttpRequestData;

public interface MappedExecutor {

    Object execute(Object... parameters) throws Exception;
    Object execute(HttpRequestData requestData) throws Exception;

    HttpMethod getHttpMethod();
}
