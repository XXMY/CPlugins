package com.cfw.plugins.mq.rabbitmq.exchange;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Exchange collection contains all defined exchanges on the same JVM.<br/>
 * Sometimes may share exchanges between different type senders, using<br/>
 * a collection helps a lot.
 *
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

    public static AbstractExchange getExchange(String exchangeType,String exchangeName, boolean create){
        Map<String,AbstractExchange> exchangeMap = exchanges.get(exchangeType);
        if(exchangeMap == null && create){
            exchangeMap = new HashMap<>();
            ExchangeCollection.exchanges.put(exchangeType,exchangeMap);

            AbstractExchange exchange = ExchangeBasic.getExchange(exchangeType,exchangeName);
            exchangeMap.put(exchangeName,exchange);

            return exchange;
        }

        return exchangeMap.computeIfAbsent(exchangeName, new Function<String, AbstractExchange>() {
            @Override
            public AbstractExchange apply(String s) {
                return ExchangeBasic.getExchange(exchangeType,s);
            }
        });

    }

    public static void addExchange(String exchangeType,AbstractExchange exchange){
        if(StringUtils.isEmpty(exchangeType) || exchange == null)
            return ;

        Map<String,AbstractExchange> exchangeMap = exchanges.get(exchangeType);
        if(exchangeMap == null)
            exchangeMap = new HashMap<>();

        exchangeMap.put(exchange.getName(),exchange);
    }

    public static void addExchange(String exchangeType,String exchangeName){
        if(StringUtils.isEmpty(exchangeType) || StringUtils.isEmpty(exchangeName))
            return ;

        Map<String,AbstractExchange> exchangeMap = exchanges.get(exchangeType);
        if(exchangeMap == null)
            exchangeMap = new HashMap<>();

        exchangeMap.put(exchangeName,ExchangeBasic.getExchange(exchangeType,exchangeName));
    }
}
