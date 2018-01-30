package com.cfw.plugins.mq.rabbitmq.queue;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/7/31.
 */
public class QueueCollection {

    private static Map<String,CQueue> queues = new HashMap<>();

    public static CQueue getQueue(String queueName){
        if(queues.containsKey(queueName))
            return queues.get(queueName);

        return null;
    }

    /*public static void addQueue() {
        Queue queue = new AnonymousQueue();

        queues.put(queue.getName(),queue);
    }*/

    public static void addQueue(CQueue queue){
        if(queue == null)
            return ;
        queues.put(queue.getName(),queue);
    }
}
