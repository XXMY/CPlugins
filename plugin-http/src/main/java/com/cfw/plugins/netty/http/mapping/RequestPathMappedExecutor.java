package com.cfw.plugins.netty.http.mapping;

import com.cfw.plugins.netty.http.request.HttpRequestData;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestPathMappedExecutor implements MappedExecutor{
    private Object controller;
    private Method method;
    // parameter name, parameter type
    private Map<String,Class> parameterTypeMap;
    private Class returnType;

    public RequestPathMappedExecutor(Object controller, Method method, Map<String,Class> parameterTypeMap, Class returnType) {
        this.controller = controller;
        this.method = method;
        this.parameterTypeMap = parameterTypeMap;
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
    public Object execute(Object ... parameters) throws Exception {
        return method.invoke(this.controller,parameters);
    }

    @Override
    public Object execute(HttpRequestData requestData) throws Exception {
        if(this.parameterTypeMap == null || this.parameterTypeMap.size() == 0)
            return this.method.invoke(this.controller);

        if(requestData.getMethod() == HttpMethod.GET){
            byte parameterBytes [] = new byte[requestData.getData().readableBytes()];
            requestData.getData().readBytes(parameterBytes);

            // parameter name => parameter value
            Map<String,String> parameterMap = this.getParametersInFormData(new String(parameterBytes));
            Object parameterObjects [] = new Object[this.parameterTypeMap.size()];
            Set<String> parameterNameSet = this.parameterTypeMap.keySet();
            int i=0;
            for(String parameterName : parameterNameSet){
                parameterObjects[i] = this.parameterTypeMap.get(parameterName).cast(parameterMap.get(parameterName));
                i ++;
            }

            return this.method.invoke(this.controller,parameterObjects);

        }
        return new Object();
    }

    private Map<String,String> getParametersInFormData(String originalData){
        if(StringUtil.isNullOrEmpty(originalData))
            return null;

        Map<String,String> parameters = new HashMap<String,String>();
        String keyValueArray [] = originalData.split("&");
        for(String keyValue : keyValueArray){
            if(!keyValue.contains("=")) continue;

            String kv [] = keyValue.split("=");
            parameters.put(kv[0],kv[1]);
        }

        return parameters;
    }
}
