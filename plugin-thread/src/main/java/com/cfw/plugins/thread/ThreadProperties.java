package com.cfw.plugins.thread;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Duskrain on 2017/8/26.
 */
@Component
@ConfigurationProperties(prefix = "c.thread")
public class ThreadProperties {

    // MQ queue message listener's thread number
    private Map<String,Integer> mqQueueThreadsNumber;

    private Map<String,Integer> InboundDispatcherThreadsNumber;
    private Map<String,Integer> OutboundDispatcherThreadsNumber;

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
}
