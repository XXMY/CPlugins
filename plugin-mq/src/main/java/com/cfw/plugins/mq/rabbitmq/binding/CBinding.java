package com.cfw.plugins.mq.rabbitmq.binding;

import org.springframework.amqp.core.Binding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/9/2.
 */
public class CBinding{

    private String destination;
    private String exchange;
    private String routingKey;
    private Map<String, Object> arguments = new HashMap<>();
    private Binding.DestinationType destinationType;

    private Binding binding;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    public Binding.DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(Binding.DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public Binding getBinding() {
        return new Binding(this.destination,this.destinationType,this.exchange,this.routingKey,this.getArguments());
    }
}
