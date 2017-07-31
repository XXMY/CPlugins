package com.cfw.plugins.rmi.listener;

import com.cfw.plugins.rmi.annotation.CRmiImport;
import com.cfw.plugins.rmi.annotation.CRmiImportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Created by Cfw on 2017/5/14.
 */
@Component
public class CRmiImporterListener extends CRmiListener {
    private Log logger = LogFactory.getLog(CRmiImporterListener.class);

    static {
        rmiXmlFileRelativePath =  "rmi/rmiImporter.xml";
        rmiXmlFileAbsolutePath = System.getProperty("user.dir") +"/"+ rmiXmlFileRelativePath;
    }

    @EventListener
    public void contextRefreshedEvent(ContextRefreshedEvent event){

        if(event.getApplicationContext().getParent() == null){
            Map<String,Object> servicesMap =  event.getApplicationContext().getBeansWithAnnotation(CRmiImport.class);

            Set<String> keySet = servicesMap.keySet();
            if(servicesMap == null || servicesMap.size() == 0)
                return ;

            try{
                for(String serviceName : keySet){
                    Object service = servicesMap.get(serviceName);
                    Field [] fields = service.getClass().getDeclaredFields();
                    if(fields == null || fields.length == 0)
                        continue;

                    for(Field field : fields){
                        if(field.getDeclaredAnnotation(CRmiImportService.class) == null)
                            continue;

                        Class fieldServiceClass =  field.getType();
                        String fieldServiceClassSimpleName = fieldServiceClass.getSimpleName();
                        char [] fieldServiceClassSimpleNameChar = fieldServiceClassSimpleName.toCharArray();
                        fieldServiceClassSimpleNameChar[0] = fieldServiceClassSimpleName.toLowerCase().charAt(0);

                        String fieldServiceBeanName = new String(fieldServiceClassSimpleNameChar);

                        this.exportServiceIntoXml(fieldServiceClass,fieldServiceBeanName);
                    }

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
     * @param applicationContext
     * @author CaiFangwei
     * @since 2017-5-14 20:51:49
     */
    @Override
    protected void loadXml(String xmlFilePath, ApplicationContext applicationContext) throws IOException {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory());
        xmlBeanDefinitionReader.setEntityResolver(new ResourceEntityResolver(applicationContext));

        FileSystemResourceLoader loader = new FileSystemResourceLoader();
        xmlBeanDefinitionReader.loadBeanDefinitions(loader.getResource(xmlFilePath));
    }

    @Override
    protected boolean appendBean(Class interfaceClass, String serviceName, String filePath) throws IOException {
        String importerServiceUrl;

        if(StringUtils.isEmpty(importerServiceUrl = properties.getImporterServiceMap().get(serviceName))){
            this.logger.warn("Service name: " + serviceName + " not found in map of importer services");
            return false;
        }

        File rmiXmlFile = new File(filePath);
        if(!rmiXmlFile.exists()) return false;

        FileWriter fileWriter = new FileWriter(filePath,true);
        fileWriter.append("\t" + "<bean id=\"" + serviceName + "\" " + "class=\"" + RmiProxyFactoryBean.class.getName() + "\">" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"serviceUrl\" value=\"" + importerServiceUrl +"\"/>" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"serviceInterface\" value=\"" + interfaceClass.getName() +"\"/>" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"lookupStubOnStartup\" value=\"" + properties.isImporterLookupStubOnStartup() +"\"/>" + System.lineSeparator());
        fileWriter.append("\t\t" + "<property name=\"refreshStubOnConnectFailure\" value=\"" + properties.isImporterRefreshStubOnConnectFailure() +"\"/>" + System.lineSeparator());

        fileWriter.append("\t" + "</bean>" + System.lineSeparator());

        fileWriter.flush();
        fileWriter.close();

        return true;
    }

}
