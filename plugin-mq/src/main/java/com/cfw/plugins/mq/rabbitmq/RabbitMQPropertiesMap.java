package com.cfw.plugins.mq.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rabbitmq.properties.map")
public class RabbitMQPropertiesMap {

    private Map<String,RabbitMQProperties> rabbitMQPropertiesMap;

    public Map<String, RabbitMQProperties> getRabbitMQPropertiesMap() {
        return rabbitMQPropertiesMap;
    }

    public void setRabbitMQPropertiesMap(Map<String, RabbitMQProperties> rabbitMQPropertiesMap) {
        this.rabbitMQPropertiesMap = rabbitMQPropertiesMap;
    }
}
