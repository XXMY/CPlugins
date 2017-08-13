package com.cfw.plugins.mq.rabbitmq.binding;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
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

    public static void addBinding(String exchangeType,String exchangeName,String queueName,String routingKey, Channel channel) throws IOException {
        String bindingName = exchangeType + "/" +exchangeName + "/" + queueName + "/" + routingKey;

        if(StringUtils.isEmpty(exchangeType) || StringUtils.isEmpty(exchangeName) || StringUtils.isEmpty(queueName) || StringUtils.isEmpty(routingKey))
            return ;

        channel.queueBind(queueName,exchangeName,routingKey);

        bindings.put(bindingName,new Binding(queueName,null,exchangeName,routingKey,null));
    }
}
