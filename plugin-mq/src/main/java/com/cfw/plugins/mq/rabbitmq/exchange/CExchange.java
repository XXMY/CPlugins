package com.cfw.plugins.mq.rabbitmq.exchange;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.ExchangeTypes;

import java.util.Map;

/**
 * Created by Duskrain on 2017/8/27.
 */
public class CExchange extends AbstractExchange{

    private ExchangeTypes exchangeType;

    /**
     * Construct a new durable, non-auto-delete Exchange with the provided name.
     *
     * @param name the name of the exchange.
     */
    public CExchange(ExchangeTypes exchangeType,String name) {
        super(name);
        this.exchangeType = exchangeType;
    }

    /**
     * Construct a new Exchange, given a name, durability flag, auto-delete flag.
     *
     * @param name       the name of the exchange.
     * @param durable    true if we are declaring a durable exchange (the exchange will
     *                   survive a server restart)
     * @param autoDelete true if the server should delete the exchange when it is no
     */
    public CExchange(ExchangeTypes exchangeType,String name, boolean durable, boolean autoDelete) {
        super(name, durable, autoDelete);
        this.exchangeType = exchangeType;
    }

    /**
     * Construct a new Exchange, given a name, durability flag, and auto-delete flag, and
     * arguments.
     *
     * @param name       the name of the exchange.
     * @param durable    true if we are declaring a durable exchange (the exchange will
     *                   survive a server restart)
     * @param autoDelete true if the server should delete the exchange when it is no
     *                   longer in use
     * @param arguments  the arguments used to declare the exchange
     */
    public CExchange(ExchangeTypes exchangeType,String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
        super(name, durable, autoDelete, arguments);
        this.exchangeType = exchangeType;
    }

    @Override
    public String getType() {
        return this.exchangeType.toString();
    }

    @Override
    public String toString() {
        return "CExchange{" +
                "exchangeType=" + exchangeType +
                '}';
    }
}
