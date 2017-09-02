package com.cfw.plugins.mq.rabbitmq.rpc;

import com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch.OutboundDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Duskrain on 2017/9/2.
 */
@Configuration
public class RpcConfiguration {

    @Bean("rpcClientOutboundDispatcher")
    public OutboundDispatcher createRpcClientOutboundDispatcher(){

        return new OutboundDispatcher();
    }

}
