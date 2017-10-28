package com.cfw.plugins.mq.rabbitmq.rpc;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

/**
 * Created by Duskrain on 2017/9/10.
 */
public class AmqpInvokerExtendedServiceExporter extends AmqpInvokerServiceExporter {

    @Override
    public void onMessage(Message message) {
        Address replyToAddress = message.getMessageProperties().getReplyToAddress();
        if (replyToAddress == null) {
            throw new AmqpRejectAndDontRequeueException("No replyToAddress in inbound AMQP Message");
        }

        Object invocationRaw = this.getMessageConverter().fromMessage(message);

        RemoteInvocationResult remoteInvocationResult;
        if (invocationRaw == null || !(invocationRaw instanceof RemoteInvocation)) {
            remoteInvocationResult =  new RemoteInvocationResult(
                    new IllegalArgumentException("The message does not contain a RemoteInvocation payload"));
        }
        else {
            RemoteInvocation invocation = (RemoteInvocation) invocationRaw;
            remoteInvocationResult = invokeAndCreateResult(invocation, getService());
        }

        this.send(remoteInvocationResult,replyToAddress,message);
    }

    private void send(Object object, Address replyToAddress, Message requestMesage){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationIdString(requestMesage.getMessageProperties().getCorrelationIdString());
        Message responseMessage = this.getMessageConverter().toMessage(object,messageProperties);

        this.getAmqpTemplate().send(replyToAddress.getExchangeName(),replyToAddress.getRoutingKey(),responseMessage);
    }
}
