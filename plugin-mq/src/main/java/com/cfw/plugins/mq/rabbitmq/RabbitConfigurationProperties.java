package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.BindingCollection;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Duskrain on 2017/8/1.
 */
@Component
@ConfigurationProperties(prefix = "c.rabbitmq")
public class RabbitConfigurationProperties {

    private String rabbitConfigurationXmlPath;

    // exchangeType/exchangeName/queueName/routingKeys
    private List<String> bindingRelations;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<String> getBindingRelations() {
        return bindingRelations;
    }

    public void setBindingRelations(List<String> bindingRelations) throws IOException {
        this.bindingRelations = bindingRelations;
        this.initBindingRelations();
    }

    private void initBindingRelations() throws IOException {
        Connection connection = this.rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);

        try{
            for(String bindingRelation : bindingRelations){
                String [] relationArray = bindingRelation.split("/",4);
                if(relationArray.length != 4) continue;

                String exchangeType = relationArray[0];
                String exchangeName = relationArray[1];
                String queueName = relationArray[2];
                String routingKeys = relationArray[3];

                ExchangeCollection.addExchange(exchangeType,exchangeName,channel);
                QueueCollection.addQueue(queueName,channel);

                String [] routingKeyArray = routingKeys.split(",");
                for(String routingKey : routingKeyArray){
                    BindingCollection.addBinding(exchangeType,exchangeName,queueName,routingKey,channel);
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

    public String getRabbitConfigurationXmlPath() {
        return rabbitConfigurationXmlPath;
    }

    public void setRabbitConfigurationXmlPath(String rabbitConfigurationXmlPath) {
        this.rabbitConfigurationXmlPath = rabbitConfigurationXmlPath;
    }
}
