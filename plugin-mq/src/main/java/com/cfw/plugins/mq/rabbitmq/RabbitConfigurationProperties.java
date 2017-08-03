package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.BindingCollection;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Duskrain on 2017/8/1.
 */
@Component
@ConfigurationProperties(prefix = "CRabbitMQ")
public class RabbitConfigurationProperties {

    // exchangeType/exchangeName/queueName/routingKeys
    private static String [] bindingRelations;


    public String[] getBindingRelations() {
        return bindingRelations;
    }

    public void setBindingRelations(String[] bindingRelations) {
        RabbitConfigurationProperties.bindingRelations = bindingRelations;

        this.initBindingRelations();
    }

    private void initBindingRelations(){
        for(String bindingRelation : RabbitConfigurationProperties.bindingRelations){
            String [] relationArray = bindingRelation.split("/");
            if(relationArray.length != 4) continue;

            String exchangeType = relationArray[0];
            String exchangeName = relationArray[1];
            String queueName = relationArray[2];
            String routingKeys = relationArray[3];

            ExchangeCollection.addExchange(exchangeType,exchangeName);
            QueueCollection.addQueue(queueName);

            String [] routingKeyArray = routingKeys.split(",");
            for(String routingKey : routingKeyArray){
                BindingCollection.addBinding(exchangeType,exchangeName,queueName,routingKey);
            }

        }
    }
}
