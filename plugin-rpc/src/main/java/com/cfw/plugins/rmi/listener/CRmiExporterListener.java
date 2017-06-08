package com.cfw.plugins.rmi.listener;

import com.cfw.plugins.rmi.annotation.CRmiExport;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Cfw on 2017/5/14.
 */
@Component
public class CRmiExporterListener extends CRmiListener {

    static {
        rmiXmlFileRelativePath = "rmi/rmiExporter.xml";
        rmiXmlFileAbsolutePath = System.getProperty("user.dir") + "/" + rmiXmlFileRelativePath;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @EventListener
    public void contextRefreshedEvent(ContextRefreshedEvent event) {

        if(event.getApplicationContext().getParent() == null){
            Map<String,Object> servicesMap =  event.getApplicationContext().getBeansWithAnnotation(CRmiExport.class);

            if(servicesMap == null || servicesMap.size() == 0)
                return ;

            Set<String> keySet = servicesMap.keySet();
            try{
                for(String serviceBeanName : keySet){
                    Object service = servicesMap.get(serviceBeanName);
                    Class [] interfaces = service.getClass().getInterfaces();
                    Class interfaceClass = null;
                    for(Class serviceInterface : interfaces){
                        // Search direct interface as exporter interface
                        if(service.getClass().getSimpleName().startsWith(serviceInterface.getSimpleName())){
                            interfaceClass = serviceInterface;
                        }
                    }

                    this.exportServiceIntoXml(interfaceClass,serviceBeanName);
                }

                if(!first){
                    this.tailXmlFile(rmiXmlFileAbsolutePath);
                    this.loadXml(rmiXmlFileRelativePath,event.getApplicationContext());
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Load xml file into Spring IOC container
     *
     * @param xmlFilePath
     * @param context
     * @author CaiFangwei
     * @since 2017-5-14 20:51:49
     */
    @Override
    protected void loadXml(String xmlFilePath, ApplicationContext context) {
        new FileSystemXmlApplicationContext(new String[]{xmlFilePath},context);
    }

    @Override
    protected boolean appendBean(Class interfaceClass,String serviceBeanName,String filePath) throws IOException{
        File rmiXmlFile = new File(filePath);
        if(!rmiXmlFile.exists()) return false;

        FileWriter fileWriter = new FileWriter(filePath,true);
        fileWriter.append("\t" + "<bean id=\"" + serviceBeanName + "Exporter\" " + "class=\"" + RmiServiceExporter.class.getName() + "\">" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"serviceName\" value=\"" + serviceBeanName +"\"/>" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"service\" ref=\"" + serviceBeanName +"\"/>" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"serviceInterface\" value=\"" + interfaceClass.getName() +"\"/>" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"registryPort\" value=\"" + properties.getExporterPort() +"\"/>" + System.lineSeparator());
        fileWriter.append("\t" + "</bean>" + System.lineSeparator());

        fileWriter.flush();
        fileWriter.close();

        return true;
    }

    /**
     * This method cannot export service as rmi service.
     * @param interfaceClass
     * @param serviceName
     * @param service
     * @author CaiFangwei
     * @since 2017-5-14 20:41:05
     */
    @Deprecated
    private void exportService(Class interfaceClass,String serviceName,Object service){
        RmiServiceExporter serviceExporter = new RmiServiceExporter();
        serviceExporter.setServiceInterface(interfaceClass);
        serviceExporter.setServiceName(serviceName);
        serviceExporter.setService(service);
        serviceExporter.setRegistryPort(properties.getExporterPort());

        this.registerBean(serviceName+"Exporter",serviceExporter);
    }

    /**
     * This method cannot export service as rmi service.
     * @param beanName
     * @param serviceExporter
     */
    @Deprecated
    private void registerBean(String beanName,RmiServiceExporter serviceExporter){
        //DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        DefaultListableBeanFactory beanFactory = null;
        if(beanFactory.containsBeanDefinition(beanName))
            return ;

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(serviceExporter.getClass());
        beanDefinition.setSource(serviceExporter);

        beanFactory.registerBeanDefinition(beanName,beanDefinition);

    }
}
