package com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch;

/**
 * Created by Duskrain on 2017/9/2.
 */
// It is deprecated as going to use spring's AmqpProxyFactoryBean.
//@Configuration
@Deprecated
public class OutboundConfiguration {

    //@Bean("rpcClientOutboundDispatcher")
    public OutboundDispatcher createRpcClientOutboundDispatcher(){

        return new OutboundDispatcher();
    }

}
