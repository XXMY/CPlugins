package com.cfw.plugins.mq.rabbitmq.rpc;

import com.cfw.plugins.mq.rabbitmq.RabbitConfigurationProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Cfw on 2017/9/9.
 */
@Component
public class SimpleMessageExtendConverter extends SimpleMessageConverter {

    @Autowired
    private RabbitConfigurationProperties configurationProperties;

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {

        messageProperties.setExpiration(configurationProperties.getRpcMessageExpiration());
        messageProperties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);

        return super.createMessage(object, messageProperties);
    }
}
