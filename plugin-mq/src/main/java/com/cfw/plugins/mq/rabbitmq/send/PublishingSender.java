package com.cfw.plugins.mq.rabbitmq.send;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class PublishingSender extends AbstractSender {

    public PublishingSender(String exchangeName, RabbitTemplate rabbitTemplate) {
        super(ExchangeTypes.FANOUT, exchangeName,null,rabbitTemplate);
    }

    @Override
    public void send(String message) {
        this.getRabbitTemplate().convertAndSend(this.getExchange().getName(),this.getRoutingKey(),message);
    }
}
