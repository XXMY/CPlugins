package com.cfw.plugins.mq.rabbitmq.binding;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Binding;

import java.io.IOException;

/**
 * Created by Duskrain on 2017/9/1.
 */
public class BindingInitializer {

    public static void initializeBinding(Binding binding, Channel channel) throws IOException {
        switch (binding.getDestinationType()){
            case QUEUE:
                channel.queueBind(binding.getDestination(),binding.getExchange(),binding.getRoutingKey(),binding.getArguments());
                break;
            case EXCHANGE:
                channel.exchangeBind(binding.getDestination(),binding.getExchange(),binding.getRoutingKey(),binding.getArguments());
                break;
            default:
                break;
        }
    }
}