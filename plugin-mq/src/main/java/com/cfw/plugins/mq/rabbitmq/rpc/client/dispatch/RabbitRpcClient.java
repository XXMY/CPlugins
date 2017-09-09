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

    static Object callRemoteProcedure(String service, String method,  List<Object> data, RoutingSender routingSender) throws Exception {
        RemoteProcedureRequest request = constructRequest(service, method, data);

        RemoteProcedureResponse response = (RemoteProcedureResponse) routingSender.callRemoteProcedure(request);

        if(response.getE() != null)
            throw response.getE();

        return response.getResult();

    }

    static  <T> T callRemoteProcedure(String service, String method,  List<Object> data,Class<T> clazz,RoutingSender routingSender) throws Exception {
        RemoteProcedureRequest request = constructRequest(service, method, data);

        RemoteProcedureResponse response = (RemoteProcedureResponse) routingSender.callRemoteProcedure(request);

        if(response.getE() != null)
            throw response.getE();

        return (T)response.getResult();

    }

    private static RemoteProcedureRequest constructRequest(String service, String method, List<Object> data){
        RemoteProcedureRequest request = new RemoteProcedureRequest();
        request.setService(service);
        request.setMethod(method);
        request.setData(data);
        request.setRequetId(UUID.randomUUID().toString());

        return request;
    }

}
