package com.cfw.plugins.mq.rabbitmq.rpc.dispatch;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/8/26.
 */
public class Selector {

    private final static Map<String,Object> collection = new HashMap<>();

    public static void put(String name, Object object){
        Selector.collection.put(name,object);
    }

    public static Object get(String name){
        if(StringUtils.isEmpty(name))
            return null;

        return Selector.collection.get(name);
    }
}
