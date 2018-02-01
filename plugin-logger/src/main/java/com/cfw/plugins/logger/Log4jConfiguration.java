package com.cfw.plugins.logger;

import com.cfw.plugins.logger.appender.mq.RabbitMQAppender;
import com.cfw.plugins.mq.rabbitmq.RabbitMQInitializer;
import com.cfw.plugins.mq.rabbitmq.RabbitMQProperties;
import com.cfw.plugins.mq.rabbitmq.queue.CQueue;
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
        for(RabbitMQProperties rabbitMQProperties : rabbitMQInitializer.getRabbitMQPropertiesMap().values()){
            if(rabbitMQProperties.getUsage() != RabbitMQProperties.Usage.LOG)
                continue;

            CQueue queue = rabbitMQProperties.getQueues().get(0);
            RabbitMQAppender.setSender(new QueueSender(queue.getName(),rabbitTemplate));

            break;
        }
    }
}
