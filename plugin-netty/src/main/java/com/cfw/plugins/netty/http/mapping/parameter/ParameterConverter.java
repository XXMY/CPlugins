package com.cfw.plugins.netty.http.mapping.parameter;

import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * To construct handler method's parameters from
 * different http body content type.
 * @author CaiFangwei
 * @since  2017年12月4日 10点04分
 */
public interface ParameterConverter {

    /**
     * Main method of constructor.
     * @param httpBodyBytes http body in bytes
     * @param parameterTypeMap handler method's parameter
     * @return Object []
     * @author CaiFangwei
     * @sine 2017年12月4日 10点14分
     */
    Object [] convert(ByteBuf httpBody, Map<String, Class> parameterTypeMap);

}
