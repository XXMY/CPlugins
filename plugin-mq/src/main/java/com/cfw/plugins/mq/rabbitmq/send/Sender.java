package com.cfw.plugins.mq.rabbitmq.send;

/**
 * Define some interfaces
 *
 * Created by Duskrain on 2017/7/31.
 */
public interface Sender {

    /**
     * Main method for sending messages to exchange.<br/>
     * Classes need to implement this method for sending messages
     * in different situations.
     * @param message The message to send.
     */
    void send(String message);
}
