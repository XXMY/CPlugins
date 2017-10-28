package com.cfw.plugins.mq.rabbitmq.rpc.server;

import com.cfw.plugins.mq.rabbitmq.rpc.server.annotation.CRpcService;
import com.cfw.plugins.mq.rabbitmq.rpc.server.dispatch.Selector;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;
import java.util.Set;

/**
 * Created by Duskrain on 2017/8/27.
 */
//@Component
// It is deprecated as going to use spring's AmqpInvokerServiceExporter.
@Deprecated
public class ServiceExporter {

    @EventListener
    public void exposeService(ContextRefreshedEvent event){
        if(event.getApplicationContext().getParent() == null){
            Map<String,Object> servicesMap =  event.getApplicationContext().getBeansWithAnnotation(CRpcService.class);

            if(servicesMap == null || servicesMap.size() == 0)
                return ;

            Set<String> keySet = servicesMap.keySet();
            try{
                for(String serviceBeanName : keySet){
                    Object service = servicesMap.get(serviceBeanName);
                    Class [] interfaces = service.getClass().getInterfaces();
                    for(Class serviceInterface : interfaces){
                        // Search direct interface as exporter interface
                        if(service.getClass().getSimpleName().startsWith(serviceInterface.getSimpleName())){
                            Selector.put(serviceInterface.getName(),service);
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
