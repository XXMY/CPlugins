package com.cfw.plugins.netty.http.mapping.parameter;

import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * A tool to use ParameterConverter-.
 * @author CaiFanwei
 * @since 2017年12月4日 10点33分
 */
public class ParameterUtils {

    private static final FormedDataConverter formedDataConstructor = new FormedDataConverter();
    private static final ApplicationJsonConverter applicationJsonConverter = new ApplicationJsonConverter();

    public static Object[] convertFormedData(ByteBuf httpBody, Map<String,Class> parameterType){

        return formedDataConstructor.convert(httpBody,parameterType);
    }

    public static Object[] convertApplicationJson(ByteBuf httpBody, Map<String,Class> parameterType){

        return applicationJsonConverter.convert(httpBody,parameterType);
    }

}
