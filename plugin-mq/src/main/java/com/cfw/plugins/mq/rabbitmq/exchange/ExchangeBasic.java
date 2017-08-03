package com.cfw.plugins.mq.rabbitmq.exchange;

import org.springframework.amqp.core.*;

import java.util.Map;

/**
 * Created by Duskrain on 2017/7/31.
 */
public class ExchangeBasic {

    public static AbstractExchange getExchange(String type, String name){
        return ExchangeBasic.getExchange(type,name,false,false,null);
    }

    public static AbstractExchange getExchange(String type, String name,boolean durable){
        return ExchangeBasic.getExchange(type,name,durable,false,null);
    }

    public static AbstractExchange getExchange(String type, String name,boolean durable,boolean autoDelete){
        return ExchangeBasic.getExchange(type,name,durable,autoDelete,null);
    }

    public static AbstractExchange getExchange(String type,String name,boolean durable,boolean autoDelete,Map<String, Object> arguments){
        switch(type){
            case ExchangeTypes.DIRECT:
                return new DirectExchange(name,durable,autoDelete,arguments);
            case ExchangeTypes.FANOUT:
                return new FanoutExchange(name,durable,autoDelete,arguments);
            case ExchangeTypes.HEADERS:
                return new HeadersExchange(name,durable,autoDelete,arguments);
            case ExchangeTypes.SYSTEM:
                return null;
            case ExchangeTypes.TOPIC:
                return new TopicExchange(name,durable,autoDelete,arguments);
            default:
                return null;
        }
    }
}
