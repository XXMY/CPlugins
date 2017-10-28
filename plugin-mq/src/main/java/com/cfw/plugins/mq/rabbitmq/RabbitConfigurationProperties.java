package com.cfw.plugins.mq.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Duskrain on 2017/8/1.
 */
@Component("rabbitConfigurationProperties")
@ConfigurationProperties(prefix = "c.rabbitmq")
public class RabbitConfigurationProperties {

    private String rabbitConfigurationXmlPath;

    // MQ queue message listener's thread number
    private Map<String,Integer> mqQueueThreadsNumber;
    private Map<String,Integer> InboundDispatcherThreadsNumber;
    private Map<String,Integer> OutboundDispatcherThreadsNumber;

    private List<String> logQueueName;

    private boolean rpcClient;
    private boolean rpcServer;

    private String messageExpiration;

    public Map<String, Integer> getMqQueueThreadsNumber() {
        return mqQueueThreadsNumber;
    }

    public void setMqQueueThreadsNumber(Map<String, Integer> mqQueueThreadsNumber) {
        this.mqQueueThreadsNumber = mqQueueThreadsNumber;
    }

    public Map<String, Integer> getInboundDispatcherThreadsNumber() {
        return InboundDispatcherThreadsNumber;
    }

    public void setInboundDispatcherThreadsNumber(Map<String, Integer> inboundDispatcherThreadsNumber) {
        InboundDispatcherThreadsNumber = inboundDispatcherThreadsNumber;
    }

    public Map<String, Integer> getOutboundDispatcherThreadsNumber() {
        return OutboundDispatcherThreadsNumber;
    }

    public void setOutboundDispatcherThreadsNumber(Map<String, Integer> outboundDispatcherThreadsNumber) {
        OutboundDispatcherThreadsNumber = outboundDispatcherThreadsNumber;
    }

    public String getRabbitConfigurationXmlPath() {
        return rabbitConfigurationXmlPath;
    }

    public void setRabbitConfigurationXmlPath(String rabbitConfigurationXmlPath) {
        this.rabbitConfigurationXmlPath = rabbitConfigurationXmlPath;
    }

    public boolean isRpcClient() {
        return rpcClient;
    }

    public void setRpcClient(boolean rpcClient) {
        this.rpcClient = rpcClient;
    }

    public boolean isRpcServer() {
        return rpcServer;
    }

    public void setRpcServer(boolean rpcServer) {
        this.rpcServer = rpcServer;
    }

    public List<String> getLogQueueName() {
        return logQueueName;
    }

    public void setLogQueueName(List<String> logQueueName) {
        this.logQueueName = logQueueName;
    }

    public String getMessageExpiration() {
        return messageExpiration;
    }

    public void setMessageExpiration(String messageExpiration) {
        this.messageExpiration = messageExpiration;
    }
}
