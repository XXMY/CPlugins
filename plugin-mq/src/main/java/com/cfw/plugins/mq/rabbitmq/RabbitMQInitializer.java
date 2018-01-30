package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.BindingInitializer;
import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
import com.cfw.plugins.mq.rabbitmq.exchange.CExchange;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeInitializer;
import com.cfw.plugins.mq.rabbitmq.queue.CQueue;
import com.cfw.plugins.mq.rabbitmq.queue.QueueCollection;
import com.cfw.plugins.mq.rabbitmq.queue.QueueInitializer;
import com.cfw.plugins.mq.rabbitmq.rpc.client.dispatch.Selector;
import com.cfw.plugins.mq.rabbitmq.rpc.server.dispatch.InboundDispatcher;
import com.cfw.plugins.mq.rabbitmq.send.RoutingSender;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * <p>The way to initialize RabbitMQ properties within server startup progress.</p>
 * To use this initializer, an applicationContext xml file should be provided with definition of
 * this class's properties. <br/>
 * RabbitConfiguration marked with @ImportResource annotation with a specified resource location which
 * defined a bean of RabbitMQInitializer.
 * Created by Duskrain on 2017/8/1.
 */
public class RabbitMQInitializer {

    // This field won't use as initializeRpcServer and initializeRpcClient are not going to use any more.
    // commented by caifw at 2018-1-22 21:02:01
    private RabbitConfigurationProperties configurationProperties;

    private RabbitTemplate rabbitTemplate;

    private RabbitMQPropertiesMap rabbitMQPropertiesMap;

    //Deprecated
    /*
    public RabbitMQInitializer(RabbitConfigurationProperties configurationProperties,RabbitTemplate rabbitTemplate, List<RabbitMQProperties> rabbitMQPropertiesList) throws IOException {
        this.configurationProperties = configurationProperties;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQPropertiesList = rabbitMQPropertiesList;

        this.initialize();
    }
    */

    public RabbitMQInitializer(RabbitTemplate rabbitTemplate, RabbitMQPropertiesMap rabbitMQPropertiesMap) throws IOException {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQPropertiesMap = rabbitMQPropertiesMap;

        this.initialize();
    }

    public Map<String,RabbitMQProperties> getRabbitMQPropertiesMap() {
        return rabbitMQPropertiesMap.getRabbitMQPropertiesMap();
    }

    public void initialize() throws IOException {
        initializeRabbitMQ();
        //initializeRpcServer();
        //initializeRpcClient();
    }

    private void initializeRabbitMQ(){
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        try{
            for(RabbitMQProperties rabbitMQProperties : rabbitMQPropertiesMap.getRabbitMQPropertiesMap().values()){
                CExchange cExchange = rabbitMQProperties.getExchange();
                ExchangeInitializer.initializeExchange(cExchange,channel);
                ExchangeCollection.addExchange(cExchange);

                List<CQueue> queues = rabbitMQProperties.getQueues();
                for(CQueue queue : queues){
                    QueueInitializer.initializeQueue(queue,channel);
                    QueueCollection.addQueue(queue);
                }

                List<CBinding> cbindings = rabbitMQProperties.getBindings();
                if(cbindings == null)
                    continue;

                for(CBinding cbinding : cbindings){
                    BindingInitializer.initializeBinding(cbinding,channel);
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(channel != null) channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            if(connection != null ) connection.close();
        }
    }

    private void initializeRpcServer(){
        if(configurationProperties.isRpcServer() && configurationProperties.getInboundDispatcherThreadsNumber() != null && configurationProperties.getInboundDispatcherThreadsNumber().size() > 0)
            InboundDispatcher.staticStartup(rabbitTemplate,configurationProperties.getInboundDispatcherThreadsNumber());
    }

    private void initializeRpcClient(){
        if(!configurationProperties.isRpcClient())
            return ;

        for(RabbitMQProperties rabbitMQProperties : rabbitMQPropertiesMap.getRabbitMQPropertiesMap().values()){
            if(!(rabbitMQProperties.getUsage() == RabbitMQProperties.Usage.RPC))
                continue;

            for(CBinding cBinding : rabbitMQProperties.getBindings()){
                //String server = cBinding.getServer();
                //Binding binding = cBinding.getBinding();

                //RoutingSender routingSender = new RoutingSender(rabbitMQProperties.getExchange().getType(), rabbitMQProperties.getExchange().getName(),binding.getRoutingKey(),rabbitTemplate);

                //Selector.put(server,routingSender);
            }
        }
    }
}
