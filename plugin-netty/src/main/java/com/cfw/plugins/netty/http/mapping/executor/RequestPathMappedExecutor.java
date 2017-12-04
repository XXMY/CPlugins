package com.cfw.plugins.netty.http.mapping.executor;

import com.cfw.plugins.netty.http.HttpMethod;
import com.cfw.plugins.netty.http.convert.DataConverter;
import com.cfw.plugins.netty.http.mapping.parameter.ParameterUtils;
import com.cfw.plugins.netty.http.request.HttpRequestData;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestPathMappedExecutor implements MappedExecutor{
    private Object controller;
    private Method method;
    private HttpMethod httpMethod;
    // parameter name, parameter type
    private Map<String,Class> parameterTypeMap;
    private Class returnType;

    public RequestPathMappedExecutor(Object controller, Method method, Map<String,Class> parameterTypeMap, Class returnType) {
        this.controller = controller;
        this.method = method;
        this.parameterTypeMap = parameterTypeMap;
        this.returnType = returnType;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, Class> getParameterTypeMap() {
        return parameterTypeMap;
    }

    public void setParameterTypeMap(Map<String, Class> parameterTypeMap) {
        this.parameterTypeMap = parameterTypeMap;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RequestPathMappedExecutor{");
        sb.append("controller=").append(controller);
        sb.append(", method='").append(method).append('\'');
        sb.append(", parameterTypes=").append(parameterTypeMap);
        sb.append(", returnType=").append(returnType);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Object handle(Object ... parameters) throws Exception {
        return method.invoke(this.controller,parameters);
    }

    @Override
    public Object handle(HttpRequestData requestData) throws Exception {
        if(this.parameterTypeMap == null || this.parameterTypeMap.size() == 0)
            return this.method.invoke(this.controller);

        Object parameterObjects [] = new Object[0];
        if(requestData.getMethod() == HttpMethod.GET || "formed data".equals(requestData.getContentType())){
            parameterObjects = ParameterUtils.convertFormedData(requestData.getData(),this.parameterTypeMap);

        }else if(requestData.getMethod() == HttpMethod.POST && "application/json".equals(requestData.getContentType())){
            parameterObjects = ParameterUtils.convertApplicationJson(requestData.getData(),this.parameterTypeMap);
        }

        return this.method.invoke(this.controller,parameterObjects);

    }
}
