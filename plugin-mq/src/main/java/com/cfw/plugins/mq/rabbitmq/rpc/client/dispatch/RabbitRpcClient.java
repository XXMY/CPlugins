package com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch;

import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureRequest;
import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureResponse;
import com.cfw.plugins.mq.rabbitmq.send.RoutingSender;

import java.util.List;
import java.util.UUID;

/**
 * Created by Duskrain on 2017/9/2.
 */
class RabbitRpcClient {

    public static Object callRemoteProcedure(String service, String method,  List<Object> data, RoutingSender routingSender) throws Exception {
        RemoteProcedureRequest request = new RemoteProcedureRequest();
        request.setService(service);
        request.setMethod(method);
        request.setData(data);
        request.setRequetId(UUID.randomUUID().toString());

        RemoteProcedureResponse response = (RemoteProcedureResponse) routingSender.callRemoteProcedure(request);

        if(response.getE() != null)
            throw response.getE();

        return response.getResult();

    }

    public static  <T> T callRemoteProcedure(String service, String method,  List<Object> data,T clazz,RoutingSender routingSender) throws Exception {
        RemoteProcedureRequest request = new RemoteProcedureRequest();
        request.setService(service);
        request.setMethod(method);
        request.setData(data);
        request.setRequetId(UUID.randomUUID().toString());

        RemoteProcedureResponse response = (RemoteProcedureResponse) routingSender.callRemoteProcedure(request);

        if(response.getE() != null)
            throw response.getE();

        return (T)response.getResult();

    }

}
