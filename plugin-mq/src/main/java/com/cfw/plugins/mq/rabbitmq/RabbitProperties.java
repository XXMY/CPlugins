package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.exchange.CExchange;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;

import java.util.List;

/**
 * Created by Duskrain on 2017/8/28.
 */
public class RabbitProperties {

    public enum Usage {
        LOG, RPC;
    }

    private AbstractExchange exchange;

    private List<Queue> queue;

    private List<Binding> binding;

    private Usage usage;

    public AbstractExchange getExchange() {
        return exchange;
    }

    public void setExchange(AbstractExchange exchange) {
        this.exchange = exchange;
    }

    public void setExchange(CExchange exchange) {
        this.exchange = exchange;
    }

    public List<Queue> getQueue() {
        return queue;
    }

    public void setQueue(List<Queue> queue) {
        this.queue = queue;
    }

    public List<Binding> getBinding() {
        return binding;
    }

    public void setBinding(List<Binding> binding) {
        this.binding = binding;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "RabbitProperties{" +
                "exchange=" + exchange +
                ", queue=" + queue +
                ", binding=" + binding +
                ", usage=" + usage +
                '}';
    }
}
