package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.BindingInitializer;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeInitializer;
import com.cfw.plugins.mq.rabbitmq.queue.QueueInitializer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Duskrain on 2017/8/1.
 */
public class RabbitMQInitializer {

    private List<RabbitProperties> rabbitPropertiesList;

    public RabbitMQInitializer(RabbitTemplate rabbitTemplate, List<RabbitProperties> rabbitPropertiesList) throws IOException {
        this.rabbitPropertiesList = rabbitPropertiesList;
        this.initialize(rabbitTemplate);
    }

    public List<RabbitProperties> getRabbitPropertiesList() {
        return rabbitPropertiesList;
    }

    private void initialize(RabbitTemplate rabbitTemplate) throws IOException {
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        try{
            for(RabbitProperties rabbitProperties : rabbitPropertiesList){
                ExchangeInitializer.initializeExchange(rabbitProperties.getExchange(),channel);
                List<Queue> queues = rabbitProperties.getQueues();
                for(Queue queue : queues){
                    QueueInitializer.initializeQueue(queue,channel);
                }

                List<Binding> bindings = rabbitProperties.getBindings();
                for(Binding binding : bindings){
                    BindingInitializer.initializeBinding(binding,channel);
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(channel != null) channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if(connection != null ) connection.close();
        }
    }

    @Override
    public String toString() {
        return "RabbitMQInitializer{" +
                "rabbitPropertiesList=" + rabbitPropertiesList +
                '}';
    }
}
