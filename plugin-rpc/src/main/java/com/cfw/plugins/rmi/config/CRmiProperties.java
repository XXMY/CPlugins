package com.cfw.plugins.rmi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cfw on 2017/5/14.
 */
@Component
@ConfigurationProperties(prefix = "crmi")
public class CRmiProperties {

    // One project or module only has one unique host and port
    private Integer exporterPort;

    // Example: 127.0.0.1:8080/importerService
    private String [] importerServices;

    private Map<String,String> importerServiceMap;

    public Integer getExporterPort() {
        return exporterPort;
    }

    public void setExporterPort(Integer exporterPort) {
        this.exporterPort = exporterPort;
    }

    public String[] getImporterServices() {
        return importerServices;
    }

    public Map<String,String> getImporterServiceMap(){
        if(this.importerServiceMap != null && this.importerServiceMap.size() > 0)
            return this.importerServiceMap;

        this.importerServiceMap = new HashMap<String,String>();
        for (String serviceHostPort : this.importerServices){
            this.importerServiceMap.put(serviceHostPort.split("/")[1],"rmi://" + serviceHostPort);
        }

        return this.importerServiceMap;
    }

    public void setImporterServices(String[] importerServices) {
        for(String importerService : importerServices){
            if(!importerService.matches("^((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]):(\\d|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])/[a-zA-Z_\\d]+$"))
                throw new IllegalArgumentException("Importer service format wrong");
        }

        this.importerServices = importerServices;
    }
}
