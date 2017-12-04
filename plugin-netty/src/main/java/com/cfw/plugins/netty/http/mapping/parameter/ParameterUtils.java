package com.cfw.plugins.netty.http.mapping.parameter;

import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * A tool to use ParameterConverter-.
 * @author CaiFanwei
 * @since 2017年12月4日 10点33分
 */
public class ParameterUtils {

    private static final FormedDataConverter formdDataConstructor = new FormedDataConverter();

    public static Object[] convertFormedData(ByteBuf httpBody, Map<String,Class> parameterType){

        return formdDataConstructor.convert(httpBody,parameterType);
    }

    public static Object[] convertApplicationJson(ByteBuf httpBody, Map<String,Class> parameterType){

        return formdDataConstructor.convert(httpBody,parameterType);
    }

}
