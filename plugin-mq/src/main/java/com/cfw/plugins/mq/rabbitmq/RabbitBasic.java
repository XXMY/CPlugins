package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
import com.cfw.plugins.mq.rabbitmq.exchange.CExchange;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.queue.CQueue;
import org.springframework.util.StringUtils;

/**
 * Reference:
 * https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/spring-amqp/src/main/java/org/springframework/amqp/tutorials
 * <p/>
 * Created by Duskrain on 2017/7/31.
 */
public abstract class RabbitBasic {

    private CQueue queue;

    private CExchange exchange;

    private CBinding binding;

    // One producer may only produce message through one certain routing key
    // and one consumer only consume message through one certain routing key.
    private String routingKey = "";

    public RabbitBasic(){}

    public RabbitBasic(String exchangeType,String exchangeName,String routingKey){
        this.initExchange(exchangeType,exchangeName);
        this.setRoutingKey(routingKey);
    }

    private void initExchange(String exchangeType,String exchangeName){
        this.exchange = ExchangeCollection.getExchange(exchangeType,exchangeName);

    }

    public CQueue getQueue() {
        return queue;
    }

    public void setQueue(CQueue queue) {
        this.queue = queue;
    }

    public CExchange getExchange() {
        return exchange;
    }

    public void setExchange(CExchange exchange) {
        this.exchange = exchange;
    }

    public CBinding getBinding() {
        return binding;
    }

    public void setBinding(CBinding binding) {
        this.binding = binding;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        if(!StringUtils.isEmpty(routingKey))
            this.routingKey = routingKey;
    }

}
