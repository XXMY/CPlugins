package com.cfw.plugins.mq.rabbitmq.queue;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/7/31.
 */
public class QueueCollection {

    private static Map<String,Queue> queues = new HashMap<>();

    public static Queue getQueue(String queueName){
        if(queues.containsKey(queueName))
            return queues.get(queueName);

        Queue queue = new Queue(queueName);
        queues.put(queueName,queue);

        return queue;
    }

    public static void addQueue(String queueName){
        if(StringUtils.isEmpty(queueName))
            queues.put("anonymous",new AnonymousQueue());

        queues.put(queueName,new Queue(queueName));
    }
}
