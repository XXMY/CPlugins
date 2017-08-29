package com.cfw.plugins.mq.rabbitmq.rpc.handle;

import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureRequest;
import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureResponse;
import com.cfw.plugins.mq.rabbitmq.rpc.dispatch.Selector;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Duskrain on 2017/8/27.
 */
public class RequestHandler {

    private RemoteProcedureRequest request;

    public RequestHandler(RemoteProcedureRequest request){
        this.request = request;
    }

    public RemoteProcedureResponse handleRequest(){
        RemoteProcedureResponse response = new RemoteProcedureResponse();
        response.setRequestId(request.getRequetId());
        try{
            response.setResult(deal());
        }catch(Exception e){
            response.setE(e);
        }

        return response;
    }

    private Object deal() throws Exception{
        if(request == null)
            throw new Exception("Request is null");

        Object service;
        if((service = Selector.get(request.getService())) == null)
            throw new Exception("Service is empty");
        if(StringUtils.isEmpty(request.getMethod()))
            throw new Exception("Method is empty");

        Class<?> serviceClass = service.getClass();
        Method method;
        if(request.getData() == null || request.getData().size() == 0)
            method = serviceClass.getDeclaredMethod(request.getMethod());
        else{
            Class<?> [] parameterTypes = new Class[request.getData().size()];
            for(int i=0;i<request.getData().size();i++){
                parameterTypes[i] = request.getData().get(i).getClass();
            }

            method = serviceClass.getDeclaredMethod(request.getMethod(),parameterTypes);
        }

        return method.invoke(service,request.getData().toArray());
    }

}
