package com.cfw.plugins.mq.rabbitmq.receive;

import com.cfw.plugins.mq.rabbitmq.RabbitBasic;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.util.StringUtils;

/**
 * Created by Duskrain on 2017/7/31.
 */
public abstract class AbstractReceiver extends RabbitBasic implements Receiver{

    public AbstractReceiver(){}

    public AbstractReceiver(String queueName) {
        this.initQueue(queueName);
    }

    public AbstractReceiver(String queueName, String exchangeType, String exchangeName, String routingKey) {
        super(exchangeType, exchangeName, routingKey);
        this.initQueue(queueName);
        this.initBinding();
    }

    private void initBinding(){
        if(this.getExchange() instanceof FanoutExchange)
            this.setBinding(
                    BindingBuilder.bind(this.getQueue()).to((FanoutExchange) this.getExchange())
            );

        if(this.getExchange() instanceof DirectExchange)
            ;
    }

    private void initQueue(String queueName){
        if(!StringUtils.isEmpty(queueName))
            this.setQueue(QueueCollection.getQueue(queueName));
        else
            this.setQueue(new AnonymousQueue());
    }
}
