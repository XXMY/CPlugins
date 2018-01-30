package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
import com.cfw.plugins.mq.rabbitmq.exchange.CExchange;
import com.cfw.plugins.mq.rabbitmq.queue.CQueue;

import java.util.List;

/**
 * Created by Duskrain on 2017/8/28.
 */
public class RabbitMQProperties {

    public enum Usage {
        LOG, RPC;
    }

    private CExchange exchange;

    private List<CQueue> queues;

    private List<CBinding> bindings;

    private Usage usage;

    public CExchange getExchange() {
        return exchange;
    }

    public void setExchange(CExchange exchange) {
        this.exchange = exchange;
    }

    public List<CQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<CQueue> queues) {
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
}
