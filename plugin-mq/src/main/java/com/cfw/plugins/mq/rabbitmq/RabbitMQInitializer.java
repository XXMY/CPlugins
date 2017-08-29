package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeInitializer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

/**
 * Created by Duskrain on 2017/8/1.
 */
public class RabbitMQInitializer {

    private List<RabbitProperties> rabbitPropertiesList;

    public RabbitMQInitializer(RabbitTemplate rabbitTemplate, List<RabbitProperties> rabbitPropertiesList) {
        this.rabbitPropertiesList = rabbitPropertiesList;
        this.initialize(rabbitTemplate);
    }

    public List<RabbitProperties> getRabbitPropertiesList() {
        return rabbitPropertiesList;
    }

    private void initialize(RabbitTemplate rabbitTemplate){
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        for(RabbitProperties rabbitProperties : rabbitPropertiesList){
            //ExchangeCollection.addExchange(rabbitProperties.getExchange());



        }
    }

    @Override
    public String toString() {
        return "RabbitMQInitializer{" +
                "rabbitPropertiesList=" + rabbitPropertiesList +
                '}';
    }
}
