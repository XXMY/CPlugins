package com.cfw.plugins.netty.http.mapping;

public interface ExecutorMapping {

    void addExecutor(String mappingKey,MappedExecutor mappedExecutor);

    MappedExecutor getExecutor(Object mappingKey);

}
