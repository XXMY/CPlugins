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
        return queues.get(queueName);
    }

    public static void addQueue(String queueName, Channel channel) throws IOException {
        Queue queue = null;
        if(StringUtils.isEmpty(queueName)){
            queue = new AnonymousQueue();
            channel.queueDeclare(queue.getName(),false,false,true,null);
        }
        else{
            queue = new Queue(queueName);
            channel.queueDeclare(queue.getName(),false,false,false,null);
        }

        queues.put(queue.getName(),queue);
    }
}
