package com.cfw.plugins.mq.rabbitmq.rpc.server.dispatch;

import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureRequest;
import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureResponse;
import com.cfw.plugins.mq.rabbitmq.rpc.server.handle.RequestHandler;
import com.cfw.plugins.thread.AbstractThread;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;
import java.util.Set;

/**
 * Created by Duskrain on 2017/8/26.
 */
public class InboundDispatcher extends AbstractThread{

    private String queueName;

    private RabbitTemplate rabbitTemplate;

    private InboundDispatcher(String queueName,RabbitTemplate rabbitTemplate,int threadNumber){
        super("InboundDispatcher",threadNumber);
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(true){
            this.rabbitTemplate.receiveAndReply(this.queueName, new ReceiveAndReplyCallback<RemoteProcedureRequest, RemoteProcedureResponse>() {
                @Override
                public RemoteProcedureResponse handle(RemoteProcedureRequest payload) {
                    return new RequestHandler(payload).handleRequest();
                }
            });
        }
    }

    /**
     * Static way to startup RabbitMQ logger listeners.
     * @param queueThreadsMap This attribute declared how many threads to create to one queue.
     */
    public static void staticStartup(RabbitTemplate rabbitTemplate, Map<String,Integer> queueThreadsMap){
        Set<String> queueNameSet = queueThreadsMap.keySet();

        for(String queueName : queueNameSet){
            int threadsNumber = queueThreadsMap.get(queueName);
            new InboundDispatcher(queueName,rabbitTemplate,threadsNumber).startup();
        }
    }
}
