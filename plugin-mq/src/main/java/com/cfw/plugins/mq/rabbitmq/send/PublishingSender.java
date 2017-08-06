package com.cfw.plugins.mq.rabbitmq.send;

import org.springframework.amqp.core.ExchangeTypes;

/**
 * PublishingSender is a sender which used in publish and subscribe messaging mode.
 *
 * Created by Duskrain on 2017/8/2.
 */
public class PublishingSender extends AbstractSender {

    /**
     * As publish-subscribe messaging mode use FANOUT exchange in RabbitMQ,
     * so exchangeName is only required to initialize the fanout exchange to
     * a specified name.
     * @param exchangeName The name of exchange
     */
    public PublishingSender(String exchangeName) {
        super(ExchangeTypes.FANOUT, exchangeName,null);
    }

    /**
     * Main method for sending messages to exchange.
     * @param message The message to send.
     */
    @Override
    public void send(String message) {
        this.getRabbitTemplate().convertAndSend(this.getExchange().getName(),this.getRoutingKey(),message);
    }
}
