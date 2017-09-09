package com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch;

import com.cfw.plugins.mq.rabbitmq.send.RoutingSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duskrain on 2017/8/26.
 */
public class OutboundDispatcher {

    public Object dispatch(String server, String service, String method, Object ... arguments) throws Exception {
        RoutingSender routingSender = Selector.get(server);

        List<Object> argumentList = new ArrayList<>();
        for(Object argument : arguments){
            argumentList.add(argument);
        }

        return RabbitRpcClient.callRemoteProcedure(service,method,argumentList,routingSender);
    }

    public <T> T dispatch(String server, String service, String method, Class<T> clazz, Object ... arguments) throws Exception {
        RoutingSender routingSender = Selector.get(server);

        List<Object> argumentList = new ArrayList<>();
        for(Object argument : arguments){
            argumentList.add(argument);
        }

        return RabbitRpcClient.callRemoteProcedure(service,method,argumentList,clazz,routingSender);
    }



}
