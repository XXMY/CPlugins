package com.cfw.plugins.mq.rabbitmq.exchange;

import org.springframework.amqp.core.*;

import java.util.Map;

/**
 * The basic of exchanges, used to create new exchange.
 *
 * Created by Duskrain on 2017/7/31.
 */
class ExchangeBasic {

    static AbstractExchange getExchange(String type, String name){
        return ExchangeBasic.getExchange(type,name,false,false,null);
    }

    static AbstractExchange getExchange(String type, String name,boolean durable){
        return ExchangeBasic.getExchange(type,name,durable,false,null);
    }

    static AbstractExchange getExchange(String type, String name,boolean durable,boolean autoDelete){
        return ExchangeBasic.getExchange(type,name,durable,autoDelete,null);
    }

    static AbstractExchange getExchange(String type,String name,boolean durable,boolean autoDelete,Map<String, Object> arguments){
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
