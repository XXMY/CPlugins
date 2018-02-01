package com.cfw.plugins.mq.rabbitmq.rpc;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

import java.util.Arrays;

/**
 * Created by Duskrain on 2017/9/10.
 */
public class AmqpProxyExtendedFactoryBean extends AmqpProxyFactoryBean {

    private MessageExtendedPostProcesser messagePostProcesser;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        RemoteInvocation remoteInvocation = getRemoteInvocationFactory().createRemoteInvocation(invocation);
        Object rawResult;
        if (getRoutingKey() == null) {
            // Use the template's default routing key
            rawResult = this.getAmqpTemplate().convertSendAndReceive(remoteInvocation, messagePostProcesser);
        }
        else {
            rawResult = this.getAmqpTemplate().convertSendAndReceive(this.getRoutingKey(), remoteInvocation, messagePostProcesser);
        }

        if (rawResult == null) {
            throw new RemoteProxyFailureException("No reply received from '" +
                    remoteInvocation.getMethodName() +
                    "' with arguments '" +
                    Arrays.asList(remoteInvocation.getArguments()) +
                    "' - perhaps a timeout in the template?", null);
        }
        else if (!(rawResult instanceof RemoteInvocationResult)) {
            throw new RemoteProxyFailureException("Expected a result of type "
                    + RemoteInvocationResult.class.getCanonicalName() + " but found "
                    + rawResult.getClass().getCanonicalName(), null);
        }

        RemoteInvocationResult result = (RemoteInvocationResult) rawResult;
        return result.recreate();

    }

    public MessageExtendedPostProcesser getMessagePostProcesser() {
        return messagePostProcesser;
    }

    public void setMessagePostProcesser(MessageExtendedPostProcesser messagePostProcesser) {
        this.messagePostProcesser = messagePostProcesser;
    }
}
