package com.cfw.plugins.mq.rabbitmq.queue;

import com.rabbitmq.client.Channel;
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

    private static Map<String,Queue> queues = new HashMap<>();

    public static Queue getQueue(String queueName){
        if(queues.containsKey(queueName))
            return queues.get(queueName);
        else{
            Queue queue = new Queue(queueName);
            queues.put(queueName,queue);
            return queue;
        }
    }

    public static void addQueue(String queueName) throws IOException {
        Queue queue = null;
        if(StringUtils.isEmpty(queueName)){
            queue = new AnonymousQueue();
        }
        else{
            queue = new Queue(queueName);
        }

        queues.put(queue.getName(),queue);
    }
}
