package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
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

    private List<Queue> queues;

    private List<CBinding> bindings;

    private Usage usage;

    public AbstractExchange getExchange() {
        return exchange;
    }

    public void setExchange(AbstractExchange exchange) {
        this.exchange = exchange;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }

    public List<CBinding> getBindings() {
        return bindings;
    }

    public void setBindings(List<CBinding> bindings) {
        this.bindings = bindings;
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
                ", queues=" + queues +
                ", bindings=" + bindings +
                ", usage=" + usage +
                '}';
    }
}
