package com.cfw.plugins.mq.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Duskrain on 2017/7/31.
 */
public class ExchangeInitializer {

    public static void initializeExchange(CExchange exchange,Channel channel) throws IOException {
        if(exchange == null || channel == null) return;
        ExchangeInitializer.initializeExchange(exchange.getType(),exchange.getName(),exchange.isDurable(),exchange.isAutoDelete(),exchange.getArguments(),channel);
    }

    static void initializeExchange(String type, String name, Channel channel) throws IOException {
        ExchangeInitializer.initializeExchange(type,name,false,false,channel);
    }

    static void initializeExchange(String type, String name, boolean durable, Channel channel) throws IOException {
        ExchangeInitializer.initializeExchange(type,name,durable,false,channel);
    }

    private static void initializeExchange(String type, String name, boolean durable, boolean autoDelete, Channel channel) throws IOException {
        ExchangeInitializer.initializeExchange(type,name,durable,autoDelete,null,channel);
    }

    private static void initializeExchange(String type, String name, boolean durable, boolean autoDelete, Map<String, Object> arguments, Channel channel) throws IOException {
        switch(type){
            case ExchangeTypes.DIRECT:
                channel.exchangeDeclare(name,ExchangeTypes.DIRECT,durable,autoDelete,arguments);
                break;
            case ExchangeTypes.FANOUT:
                channel.exchangeDeclare(name,ExchangeTypes.FANOUT,durable,autoDelete,arguments);
                break;
            case ExchangeTypes.HEADERS:
                channel.exchangeDeclare(name,ExchangeTypes.HEADERS,durable,autoDelete,arguments);
                break;
            case ExchangeTypes.SYSTEM:
                break;
            case ExchangeTypes.TOPIC:
                channel.exchangeDeclare(name,ExchangeTypes.TOPIC,durable,autoDelete,arguments);
                break;
            default:
                break;
        }
    }
}
