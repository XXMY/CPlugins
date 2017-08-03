package com.cfw.plugins.mq.rabbitmq.send;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class RoutingSender extends AbstractSender{

    public RoutingSender(String exchangeType, String exchangeName, String routingKey) {
        super(exchangeType, exchangeName, routingKey);
    }

    @Override
    public void send(String message) {
        this.getRabbitTemplate().convertAndSend(this.getExchange().getName(),this.getRoutingKey(),message);
    }
}
