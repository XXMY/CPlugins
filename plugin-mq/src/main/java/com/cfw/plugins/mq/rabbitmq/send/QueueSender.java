package com.cfw.plugins.mq.rabbitmq.send;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * QueueSender is a sender which used in simplest messaging mode.
 *
 * Created by Duskrain on 2017/8/1.
 */
public class QueueSender extends AbstractSender {

    public QueueSender(String queueName, RabbitTemplate rabbitTemplate){
        super(queueName,rabbitTemplate);
    }

    /**
     * Main method for sending messages to RabbitMQ.
     * @param message The message to send.
     */
    @Override
    public void send(String message) {
        this.getRabbitTemplate().convertAndSend(this.getQueue().getName(),message);
    }
}
