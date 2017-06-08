package com.cfw.plugins.rmi.listener;

import com.cfw.plugins.rmi.config.CRmiProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Cfw on 2017/5/14.
 */
public abstract class CRmiListener {
    private static Log logger ;

    @Autowired
    protected CRmiProperties properties;

    protected boolean first = true;

    protected static String rmiXmlFileAbsolutePath;
    protected static String rmiXmlFileRelativePath;

    /**
     * Load xml file into Spring IOC container
     * @param xmlFilePath
     * @param context
     *
     * @author CaiFangwei
     * @since 2017-5-14 20:51:49
     */
    protected abstract void loadXml(String xmlFilePath, ApplicationContext context) throws IOException;

    protected final boolean createRmiXmlTemplateFile() throws IOException {
        File rmiXmlFolder = new File(rmiXmlFileAbsolutePath);

        if(!first && rmiXmlFolder.exists()) return true;

        if(!rmiXmlFolder.getParentFile().exists() && !rmiXmlFolder.getParentFile().mkdirs())
            return false;

        FileWriter fileWriter = new FileWriter(rmiXmlFileAbsolutePath);
        fileWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator());
        fileWriter.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"" + System.lineSeparator());
        fileWriter.append("\t" + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + System.lineSeparator());
        fileWriter.append("\t" + "xsi:schemaLocation=\"http://www.springframework.org/schema/beans" + System.lineSeparator());
        fileWriter.append("\t" + "http://www.springframework.org/schema/beans/spring-beans.xsd\">" + System.lineSeparator());

        fileWriter.flush();
        fileWriter.close();

        first = !first;
        return true;
    }

    protected final boolean tailXmlFile(String filePath) throws IOException {
        File rmiXmlFile = new File(filePath);
        if(!rmiXmlFile.exists()) return false;

        FileWriter fileWriter = new FileWriter(filePath,true);
        fileWriter.append("</beans>" + System.lineSeparator());
        fileWriter.flush();
        fileWriter.close();

        return true;

    }

    protected void exportServiceIntoXml(Class interfaceClass,String serviceName) throws IOException{
        this.createRmiXmlTemplateFile();
        this.appendBean(interfaceClass,serviceName,rmiXmlFileAbsolutePath);
    }

    protected abstract boolean appendBean(Class interfaceClass, String serviceName, String filePath) throws IOException;
}
