package com.cfw.plugins.mq.rabbitmq.receive;

import com.cfw.plugins.mq.rabbitmq.RabbitBasic;
import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
import com.cfw.plugins.mq.rabbitmq.queue.CQueue;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import org.springframework.amqp.core.Binding;
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
        CBinding binding = new CBinding();
        binding.setDestination(this.getQueue().getName());
        binding.setDestinationType(Binding.DestinationType.EXCHANGE);
        binding.setExchange(this.getExchange().getName());
        binding.setRoutingKey(this.getRoutingKey());
        binding.setArguments(null);
    }

    private void initQueue(String queueName){
        if(!StringUtils.isEmpty(queueName))
            this.setQueue(QueueCollection.getQueue(queueName));
        else
            this.setQueue(new CQueue());
    }
}
