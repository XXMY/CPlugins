package com.cfw.plugins.mq.rabbitmq;

import com.cfw.plugins.mq.rabbitmq.binding.BindingInitializer;
import com.cfw.plugins.mq.rabbitmq.binding.CBinding;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeCollection;
import com.cfw.plugins.mq.rabbitmq.exchange.ExchangeInitializer;
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

    private RabbitConfigurationProperties configurationProperties;

    private RabbitTemplate rabbitTemplate;

    private List<RabbitProperties> rabbitPropertiesList;

    public RabbitMQInitializer(RabbitConfigurationProperties configurationProperties,RabbitTemplate rabbitTemplate, List<RabbitProperties> rabbitPropertiesList) throws IOException {
        this.configurationProperties = configurationProperties;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitPropertiesList = rabbitPropertiesList;

        this.initialize();
    }

    public List<RabbitProperties> getRabbitPropertiesList() {
        return rabbitPropertiesList;
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
            for(RabbitProperties rabbitProperties : rabbitPropertiesList){
                ExchangeInitializer.initializeExchange(rabbitProperties.getExchange(),channel);
                ExchangeCollection.addExchange(rabbitProperties.getExchange());

                List<Queue> queues = rabbitProperties.getQueues();
                for(Queue queue : queues){
                    QueueInitializer.initializeQueue(queue,channel);
                    QueueCollection.addQueue(queue);
                }

                List<CBinding> cbindings = rabbitProperties.getBindings();
                if(cbindings == null)
                    continue;

                for(CBinding cbinding : cbindings){
                    BindingInitializer.initializeBinding(cbinding.getBinding(),channel);
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

        for(RabbitProperties rabbitProperties : rabbitPropertiesList){
            if(!(rabbitProperties.getUsage() == RabbitProperties.Usage.RPC))
                continue;

            for(CBinding cBinding : rabbitProperties.getBindings()){
                String server = cBinding.getServer();
                Binding binding = cBinding.getBinding();

                RoutingSender routingSender = new RoutingSender(rabbitProperties.getExchange().getType(),rabbitProperties.getExchange().getName(),binding.getRoutingKey(),rabbitTemplate);

                Selector.put(server,routingSender);
            }
        }
    }

    @Override
    public String toString() {
        return "RabbitMQInitializer{" +
                "rabbitPropertiesList=" + rabbitPropertiesList +
                '}';
    }
}
