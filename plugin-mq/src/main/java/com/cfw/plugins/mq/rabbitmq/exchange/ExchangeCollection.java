package com.cfw.plugins.mq.rabbitmq.exchange;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class ExchangeCollection {
    //Map<ExchangeType,Map<ExchangeName,Exchange>>
    private static Map<String,Map<String,CExchange>> exchanges = new HashMap<>();

    public static Map<String, Map<String, CExchange>> getExchanges() {
        return exchanges;
    }

    public static CExchange getExchange(String exchangeType,String exchangeName){
        Map<String,CExchange> exchangeMap = exchanges.get(exchangeType);
        if(exchangeMap == null || exchangeMap.size() == 0) return null;

        return exchangeMap.get(exchangeName);
    }

    public static void addExchange(CExchange exchange){
        if(exchange == null)
            return ;

        Map<String,CExchange> exchangeMap = exchanges.get(exchange.getType());
        if(exchangeMap == null) {
            exchangeMap = new HashMap<>();
            exchanges.put(exchange.getType(),exchangeMap);
        }

        exchangeMap.put(exchange.getName(),exchange);
    }
}
