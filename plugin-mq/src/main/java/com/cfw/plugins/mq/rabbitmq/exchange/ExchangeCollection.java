package com.cfw.plugins.mq.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class ExchangeCollection {
    //Map<ExchangeType,Map<ExchangeName,Exchange>>
    private static Map<String,Map<String,AbstractExchange>> exchanges = new HashMap<>();

    public static Map<String, Map<String, AbstractExchange>> getExchanges() {
        return exchanges;
    }

    public static AbstractExchange getExchange(String exchangeType,String exchangeName){
        Map<String,AbstractExchange> exchangeMap = exchanges.get(exchangeType);
        if(exchangeMap == null || exchangeMap.size() == 0) return null;

        return exchangeMap.get(exchangeName);
    }

    public static void addExchange(AbstractExchange exchange){
        if(exchange == null)
            return ;

        Map<String,AbstractExchange> exchangeMap = exchanges.get(exchange.getType());
        if(exchangeMap == null) {
            exchangeMap = new HashMap<>();
            exchanges.put(exchange.getType(),exchangeMap);
        }

        exchangeMap.put(exchange.getName(),exchange);
    }

    public static void addExchange(String exchangeType,String exchangeName, Channel channel) throws IOException {
        if(StringUtils.isEmpty(exchangeType) || StringUtils.isEmpty(exchangeName))
            return ;

        Map<String,AbstractExchange> exchangeMap = exchanges.get(exchangeType);
        if(exchangeMap == null) {
            exchangeMap = new HashMap<>();
            exchanges.put(exchangeType,exchangeMap);
        }

        exchangeMap.put(exchangeName, ExchangeInitializer.initializeExchange(exchangeType,exchangeName,channel));
    }
}
