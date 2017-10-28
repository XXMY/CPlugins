package com.cfw.plugins.mq.rabbitmq.rpc;

import com.cfw.plugins.mq.rabbitmq.RabbitConfigurationProperties;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Duskrain on 2017/9/10.
 */
@Component
public class MessageExtendedPostProcesser implements MessagePostProcessor {

    @Autowired
    private RabbitConfigurationProperties configurationProperties;

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        MessageProperties messageProperties = message.getMessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        messageProperties.setExpiration(configurationProperties.getMessageExpiration());

        return message;
    }
}
