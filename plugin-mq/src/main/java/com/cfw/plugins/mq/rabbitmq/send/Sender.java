package com.cfw.plugins.mq.rabbitmq.send;

/**
 * Created by Duskrain on 2017/7/31.
 */
public interface Sender {
    void send(String message);

    void send(byte[] bytes);

    void send(Object object);
}
