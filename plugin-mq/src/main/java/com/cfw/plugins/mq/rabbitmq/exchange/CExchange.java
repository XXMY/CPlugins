package com.cfw.plugins.mq.rabbitmq.exchange;

import org.springframework.amqp.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/8/27.
 */
public class CExchange {

    private String name;
    private String type;
    private boolean durable;
    private boolean autoDelete;
    private boolean delayed;
    private Map<String, Object> arguments = new HashMap<>();

    private AbstractExchange exchage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    public AbstractExchange getExchage() {
        switch(this.type){
            case ExchangeTypes.DIRECT:
                return new DirectExchange(this.name,this.durable,this.autoDelete,this.arguments);
            case ExchangeTypes.FANOUT:
                return new FanoutExchange(this.name,this.durable,this.autoDelete,this.arguments);
            case ExchangeTypes.HEADERS:
                return new HeadersExchange(this.name,this.durable,this.autoDelete,this.arguments);
            case ExchangeTypes.SYSTEM:
                return null;
            case ExchangeTypes.TOPIC:
                return new TopicExchange(this.name,this.durable,this.autoDelete,this.arguments);
            default:
                return null;
        }
    }
}
