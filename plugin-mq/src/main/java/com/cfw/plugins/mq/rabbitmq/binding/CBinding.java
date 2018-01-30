package com.cfw.plugins.mq.rabbitmq.binding;

import java.util.Map;

/**
 * Created by Duskrain on 2017/9/2.
 */
public class CBinding {

    private String destination;
    private String exchange;
    public enum DestinationType {
        QUEUE,
        EXCHANGE;

        private DestinationType() {
        }
    }

    private CBinding.DestinationType destinationType;
    private String routingKey;

    private Map<String, Object> arguments;

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

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
