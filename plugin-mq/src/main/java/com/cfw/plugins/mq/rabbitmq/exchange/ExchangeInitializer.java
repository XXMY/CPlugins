package com.cfw.plugins.mq.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Duskrain on 2017/7/31.
 */
public class ExchangeInitializer {

    public static void initializeExchange(){}

    public static AbstractExchange initializeExchange(String type, String name, Channel channel) throws IOException {
        return ExchangeInitializer.initializeExchange(type,name,false,false,channel);
    }

    public static AbstractExchange initializeExchange(String type, String name, boolean durable, Channel channel) throws IOException {
        return ExchangeInitializer.initializeExchange(type,name,durable,false,channel);
    }

    public static AbstractExchange initializeExchange(String type, String name, boolean durable, boolean autoDelete, Channel channel) throws IOException {
        return ExchangeInitializer.initializeExchange(type,name,durable,autoDelete,null,channel);
    }

    public static AbstractExchange initializeExchange(String type, String name, boolean durable, boolean autoDelete, Map<String, Object> arguments, Channel channel) throws IOException {
        switch(type){
            case ExchangeTypes.DIRECT:
                channel.exchangeDeclare(name,ExchangeTypes.DIRECT,durable,autoDelete,arguments);
                return new DirectExchange(name,durable,autoDelete,arguments);
            case ExchangeTypes.FANOUT:
                channel.exchangeDeclare(name,ExchangeTypes.FANOUT,durable,autoDelete,arguments);
                return new FanoutExchange(name,durable,autoDelete,arguments);
            case ExchangeTypes.HEADERS:
                channel.exchangeDeclare(name,ExchangeTypes.HEADERS,durable,autoDelete,arguments);
                return new HeadersExchange(name,durable,autoDelete,arguments);
            case ExchangeTypes.SYSTEM:
                return null;
            case ExchangeTypes.TOPIC:
                channel.exchangeDeclare(name,ExchangeTypes.TOPIC,durable,autoDelete,arguments);
                return new TopicExchange(name,durable,autoDelete,arguments);
            default:
                return null;
        }
    }
}
