package com.cfw.plugins.mq.rabbitmq.send;

import com.cfw.plugins.mq.rabbitmq.rpc.RemoteProcedureRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by Duskrain on 2017/8/2.
 */
public class RoutingSender extends AbstractSender{

    public RoutingSender(String exchangeType, String exchangeName, String routingKey, RabbitTemplate rabbitTemplate) {
        super(exchangeType, exchangeName, routingKey,rabbitTemplate);
    }

    @Override
    public void send(String message) {
        this.getRabbitTemplate().convertAndSend(this.getExchange().getName(),this.getRoutingKey(),message);
    }

    /**
     * Call remote procedure to deal request.
     * @param request The request needs to be deal.
     * @return Response data of remote call.
     */
    public Object callRemoteProcedure(RemoteProcedureRequest request){
        return this.getRabbitTemplate().convertSendAndReceive(this.getExchange().getName(),this.getRoutingKey(),request);
    }
}
