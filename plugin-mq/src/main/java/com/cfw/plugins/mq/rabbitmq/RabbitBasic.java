package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.util.StringUtils;

/**
 * Reference:
 * https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/spring-amqp/src/main/java/org/springframework/amqp/tutorials
 * <p/>
 * Created by Duskrain on 2017/7/31.
 */
public abstract class RabbitBasic {

    private Queue queue;

    private AbstractExchange exchange;

    private Binding binding;

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

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public AbstractExchange getExchange() {
        return exchange;
    }

    public void setExchange(AbstractExchange exchange) {
        this.exchange = exchange;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
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
