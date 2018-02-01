package com.cfw.plugins.mq.rabbitmq.binding;

import org.springframework.amqp.core.Binding;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class BindingCollection {

    private static Map<String,Binding> bindings = new HashMap<>();

    public static Binding getBinding(String bindingName){
        return bindings.get(bindingName);
    }

    public static void addBinding(String exchangeType,String exchangeName,String queueName,String routingKey) {
        String bindingName = exchangeType + "/" +exchangeName + "/" + queueName + "/" + routingKey;

        if(StringUtils.isEmpty(exchangeType) || StringUtils.isEmpty(exchangeName) || StringUtils.isEmpty(queueName) || StringUtils.isEmpty(routingKey))
            return ;

        bindings.put(bindingName,new Binding(queueName,null,exchangeName,routingKey,null));
    }

    public static void addBinding(CBinding cBinding) {
        String bindingName = cBinding.getDestinationType() + "/" +cBinding.getExchange() + "/" + cBinding.getDestination() + "/" + cBinding.getRoutingKey();

        if(StringUtils.isEmpty(cBinding.getDestinationType()) || StringUtils.isEmpty(cBinding.getExchange()) || StringUtils.isEmpty(cBinding.getDestination()) || StringUtils.isEmpty(cBinding.getRoutingKey()))
            return ;

        bindings.put(bindingName,cBinding.getBinding());
    }
}
