package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.BindingInitializer;
import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
import com.cfw.plugins.mq.rabbitmq.exchange.CExchange;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeInitializer;
import com.cfw.plugins.mq.rabbitmq.queue.CQueue;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import com.cfw.plugins.mq.rabbitmq.queue.QueueInitializer;
import com.cfw.plugins.mq.rabbitmq.rpc.server.dispatch.InboundDispatcher;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * <p>The way to initialize RabbitMQ properties within server startup progress.</p>
 * To use this initializer, an applicationContext xml file should be provided with definition of
 * this class's properties. <br/>
 * RabbitConfiguration marked with @ImportResource annotation with a specified resource location which
 * defined a bean of RabbitMQInitializer.
 * Created by Duskrain on 2017/8/1.
 */
@Component
public class RabbitMQInitializer {

    private RabbitTemplate rabbitTemplate;

    private RabbitMQPropertiesMap rabbitMQPropertiesMap;

    @Autowired
    public RabbitMQInitializer(RabbitTemplate rabbitTemplate, RabbitMQPropertiesMap rabbitMQPropertiesMap) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQPropertiesMap = rabbitMQPropertiesMap;

        this.initialize();
    }

    public Map<String,RabbitMQProperties> getRabbitMQPropertiesMap() {
        return rabbitMQPropertiesMap.getRabbitMQPropertiesMap();
    }

    public void initialize() {
        initializeRabbitMQ();
    }

    private void initializeRabbitMQ(){
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        try{
            for(RabbitMQProperties rabbitMQProperties : rabbitMQPropertiesMap.getRabbitMQPropertiesMap().values()){
                CExchange cExchange = rabbitMQProperties.getExchange();
                ExchangeInitializer.initializeExchange(cExchange,channel);
                ExchangeCollection.addExchange(cExchange);

                List<CQueue> queues = rabbitMQProperties.getQueues();
                for(CQueue queue : queues){
                    QueueInitializer.initializeQueue(queue,channel);
                    QueueCollection.addQueue(queue);
                }

                List<CBinding> cbindings = rabbitMQProperties.getBindings();
                if(cbindings == null)
                    continue;

                for(CBinding cbinding : cbindings){
                    BindingInitializer.initializeBinding(cbinding,channel);
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(channel != null) channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            connection.close();
        }
    }

}
