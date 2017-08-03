package com.cfw.plugins.mq.rabbitmq.send;

import com.cfw.plugins.mq.rabbitmq.RabbitBasic;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;

/**
 * Created by Duskrain on 2017/7/31.
 */
public abstract class AbstractSender extends RabbitBasic implements Sender{

    private RabbitTemplate rabbitTemplate;

    public AbstractSender(String queueName, RabbitTemplate rabbitTemplate) {
        this.initQueue(queueName);
        this.rabbitTemplate = rabbitTemplate;

    }

    public AbstractSender(String exchangeType,String exchangeName,String routingKey){
        super(exchangeType,exchangeName,routingKey);
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    private void initQueue(String queueName){
        if(!StringUtils.isEmpty(queueName))
            this.setQueue(QueueCollection.getQueue(queueName));
    }

}
