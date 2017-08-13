package com.cfw.plugins.mq.rabbitmq.exchange;

import java.util.Map;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class ExchangeProperties {

    private String name;
    private String type;
    private boolean durable;
    private boolean autoDelete;
    private Map<String,Object> arguments;

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

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
