package com.cfw.plugins.netty.http.convert;

import com.alibaba.fastjson.JSONObject;

/**
 * @author CaiFangwei
 * @since 2017年11月18日 22点11分
 */
public class DataConverter {

    public static Object convert(String source, Class destination){
        String simpleName = destination.getSimpleName();

        Object result = null;

        if("String".equals(simpleName))
            result = source;
        else if("Integer".equals(simpleName) || "int".equals(simpleName))
            result = Integer.parseInt(source);
        else if("Double".equals(simpleName) || "double".equals(simpleName))
            result = Double.parseDouble(source);
        else if("Boolean".equals(simpleName) || "boolean".equals(simpleName))
            result = Boolean.parseBoolean(source);
        else
            result = JSONObject.parseObject(source,destination);

        return result;
    }
}
