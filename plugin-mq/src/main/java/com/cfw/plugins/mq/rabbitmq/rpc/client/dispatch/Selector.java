package com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch;

import com.cfw.plugins.mq.rabbitmq.send.RoutingSender;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/9/2.
 */
public class Selector {

    private final static Map<String,RoutingSender> collection = new HashMap<>();

    public static void put(String server, RoutingSender routingSender){
        collection.put(server,routingSender);
    }

    public static RoutingSender get(String server){
        if(StringUtils.isEmpty(server))
            return null;

        return collection.get(server);
    }

}
