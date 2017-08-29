package com.cfw.plugins.mq.rabbitmq.listener;

import com.cfw.plugins.mq.rabbitmq.RabbitConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Created by Duskrain on 2017/8/28.
 */
@Component
public class RabbitMQInitializationListener {

    @EventListener
    @Autowired
    public void initialize(ContextRefreshedEvent event, RabbitConfigurationProperties configurationProperties){
        try{
            ApplicationContext applicationContext = event.getApplicationContext();
            XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory());
            xmlBeanDefinitionReader.setEntityResolver(new ResourceEntityResolver(applicationContext));

            FileSystemResourceLoader loader = new FileSystemResourceLoader();
            xmlBeanDefinitionReader.loadBeanDefinitions(loader.getResource(configurationProperties.getRabbitConfigurationXmlPath()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
