package com.cfw.plugins.netty.http.mapping.parameter;

import com.cfw.plugins.netty.http.convert.DataConverter;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author CaiFangwei
 * @since 2017年12月4日 10点16分
 */
public class FormedDataConverter implements ParameterConverter {

    /**
     * Main method of constructor.
     *
     * @param httpBodyBytes
     *         http body in bytes
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

        // parameter name => parameter value
        Map<String,String> parameterMap = this.getParametersInFormData(new String(parameterBytes));
        Object parameterObjects [] = new Object[parameterTypeMap.size()];
        Set<String> parameterNameSet = parameterTypeMap.keySet();
        int i=0;
        for(String parameterName : parameterNameSet){
            parameterObjects[i] = DataConverter.convert(parameterMap.get(parameterName),parameterTypeMap.get(parameterName));
            i ++;
        }

        return parameterObjects;
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
