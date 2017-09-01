package com.cfw.plugins.mq.rabbitmq.queue;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Queue;

import java.io.IOException;

/**
 * Created by Duskrain on 2017/8/29.
 */
public class QueueInitializer {

    public static void initializeQueue(Queue queue, Channel channel) throws IOException {
        channel.queueDeclare(queue.getName(),queue.isDurable(),queue.isExclusive(),queue.isAutoDelete(),queue.getArguments());
    }
}
