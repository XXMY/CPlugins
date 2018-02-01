package com.cfw.plugins.mq.rabbitmq.queue;

import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * Created by Duskrain on 2017/8/29.
 */
public class QueueInitializer {

    public static void initializeQueue(CQueue queue, Channel channel) throws IOException {
        if(queue == null || channel == null)
            return ;
        channel.queueDeclare(queue.getName(),queue.isDurable(),queue.isExclusive(),queue.isAutoDelete(),queue.getArguments());
    }
}
