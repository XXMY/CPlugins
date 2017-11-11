package com.cfw.plugins.netty.http.mapping;

public interface MappedExecutor {

    Object execute(Object ... parameters) throws Exception;
}
