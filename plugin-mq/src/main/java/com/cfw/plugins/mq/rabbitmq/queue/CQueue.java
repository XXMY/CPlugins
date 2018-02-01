package com.cfw.plugins.mq.rabbitmq.queue;

import org.springframework.amqp.core.Queue;

import java.util.HashMap;
import java.util.Map;

public class CQueue {
    private String name;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;
    private Map<String, Object> arguments = new HashMap<>();

    private Queue queue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
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

    public Queue getQueue() {
        return new Queue(this.name,this.durable,this.exclusive,this.autoDelete,this.arguments);
    }
}
