package com.cfw.plugins.logger;

import com.cfw.plugins.logger.appender.mq.RabbitMQAppender;
import com.cfw.plugins.mq.rabbitmq.RabbitMQInitializer;
import com.cfw.plugins.mq.rabbitmq.RabbitProperties;
import com.cfw.plugins.mq.rabbitmq.send.QueueSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Duskrain on 2017/9/2.
 */
@Configuration
public class Log4jConfiguration {

    @Autowired
    public Log4jConfiguration(RabbitMQInitializer rabbitMQInitializer, RabbitTemplate rabbitTemplate){
        List<RabbitProperties> rabbitPropertiesList = rabbitMQInitializer.getRabbitPropertiesList();
        for(RabbitProperties rabbitProperties : rabbitPropertiesList){
            if(rabbitProperties.getUsage() != RabbitProperties.Usage.LOG)
                continue;

            Queue queue = rabbitProperties.getQueues().get(0);
            RabbitMQAppender.setSender(new QueueSender(queue.getName(),rabbitTemplate));

            break;
        }
    }
}
