package com.cfw.plugins.netty.http.mapping.parameter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cfw.plugins.netty.http.convert.DataConverter;
import io.netty.buffer.ByteBuf;

import java.util.Map;
import java.util.Set;


/**
 * @author CaiFangwei
 * @since
 */
public class ApplicationJsonConverter implements ParameterConverter {

    /**
     * Main method of constructor.
     *
     * @param httpBody
     * @param parameterTypeMap
     *         handler method's parameter
     * @return Object []
     * @author CaiFangwei
     * @sine 2017年12月4日 10点14分
     */
    @Override
    public Object[] convert(ByteBuf httpBody, Map<String, Class> parameterTypeMap) {
        if(httpBody == null)
            return new Object[0];

        byte parameterBytes [] = new byte[httpBody.readableBytes()];
        httpBody.readBytes(parameterBytes);

        JSONObject jsonObject = JSON.parseObject(new String(parameterBytes));

        Object parameterObjects [] = new Object[parameterTypeMap.size()];
        Set<String> parameterNameSet = parameterTypeMap.keySet();
        int i=0;
        for(String parameterName : parameterNameSet){
            parameterObjects[i] = DataConverter.convert((String)jsonObject.get(parameterName),parameterTypeMap.get(parameterName));
            i ++;
        }

       return parameterObjects;
    }
}
