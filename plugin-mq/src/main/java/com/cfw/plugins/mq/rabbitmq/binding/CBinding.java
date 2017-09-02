package com.cfw.plugins.mq.rabbitmq.binding;

import org.springframework.amqp.core.Binding;

/**
 * Created by Duskrain on 2017/9/2.
 */
public class CBinding {

    private String server;

    private Binding binding;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    @Override
    public String toString() {
        return "CBinding{" +
                "server='" + server + '\'' +
                ", binding=" + binding +
                '}';
    }
}
