package com.cfw.plugins.mq.rabbitmq.send;

/**
 * RoutingSender is a sender which sends message to an exchange and the
 * exchange will routing messages to different queues through specified
 * routing key.
 *
 * Created by Duskrain on 2017/8/2.
 */
public class RoutingSender extends AbstractSender{

    public RoutingSender(String exchangeType, String exchangeName, String routingKey) {
        super(exchangeType, exchangeName, routingKey);
    }

    /**
     * Main method for sending messages to an exchange.
     * @param message The message to send.
     */
    @Override
    public void send(String message) {
        this.getRabbitTemplate().convertAndSend(this.getExchange().getName(),this.getRoutingKey(),message);
    }
}
