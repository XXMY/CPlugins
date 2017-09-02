package com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch;

import com.cfw.plugins.mq.rabbitmq.send.RoutingSender;

import java.util.List;

/**
 * Created by Duskrain on 2017/8/26.
 */
public class OutboundDispatcher {

    public Object dispatch(String server,String service,String method,List<Object> data) throws Exception {
        RoutingSender routingSender = Selector.get(server);
        return RabbitRpcClient.callRemoteProcedure(service,method,data,routingSender);
    }

    public <T> T dispatch(String server,String service,String method,List<Object> data, T clazz ) throws Exception {
        RoutingSender routingSender = Selector.get(server);
        return RabbitRpcClient.callRemoteProcedure(service,method,data,clazz,routingSender);
    }

}
