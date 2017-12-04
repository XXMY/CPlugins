package com.cfw.plugins.netty.http.mapping;

import com.cfw.plugins.netty.http.mapping.executor.MappedExecutor;

public interface ExecutorMapping {

    void addExecutor(String mappingKey, MappedExecutor mappedExecutor);

    MappedExecutor getExecutor(Object mappingKey);

}
