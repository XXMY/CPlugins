package com.cfw.plugins.mq.rabbitmq.rpc;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * Created by Duskrain on 2017/9/10.
 */
public class SimpleMessageExtendedListenerContainer extends SimpleMessageListenerContainer {

    /**
     * Creates the specified number of concurrent consumers, in the form of a Rabbit Channel plus associated
     * MessageConsumer.
     *
     * @throws Exception Any Exception.
     */
    @Override
    protected void doInitialize() throws Exception {
        super.doInitialize();
        Object listener = getMessageListener();
        if(listener instanceof RabbitTemplate){
            RabbitTemplate rabbitTemplate = (RabbitTemplate) listener;
            Queue replyQueue = getRabbitAdmin().declareQueue();
            rabbitTemplate.setReplyAddress(replyQueue.getName());
            setQueues(replyQueue);
        }
    }
}
