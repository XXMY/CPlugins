package com.cfw.plugins.mq.rabbitmq.receive;

/**
 * Created by Duskrain on 2017/7/31.
 */
public interface Receiver {

    void receive(String message);
}
