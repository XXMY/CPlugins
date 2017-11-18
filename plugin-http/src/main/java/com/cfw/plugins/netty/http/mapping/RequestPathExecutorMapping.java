package com.cfw.plugins.netty.http.mapping;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestPathExecutorMapping implements ExecutorMapping{

    private Map<String,MappedExecutor> executorMap;

    public RequestPathExecutorMapping() {
        this.executorMap = new HashMap<String,MappedExecutor>();
    }

    public void addExecutor(String mappingKey,MappedExecutor mappedExecutor){
        this.executorMap.put(mappingKey,mappedExecutor);
    }

    @Override
    public MappedExecutor getExecutor(Object mappingKey) {
        return this.executorMap.get(mappingKey);
    }

}
