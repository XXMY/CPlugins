package com.cfw.plugins.netty.http.mapping;

import java.lang.reflect.Method;
import java.util.Map;

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
}
