package com.cfw.plugins.rmi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Cfw on 2017/5/14.
 */
@Component
@ConfigurationProperties(prefix = "crmi")
public class CRmiProperties {

    // One project or module only has one unique host and port
    private Integer exporterPort;

    // One project or module may contains several different importer host and port
    private String importerHost;
    private String importerPort;

    public Integer getExporterPort() {
        return exporterPort;
    }

    public void setExporterPort(Integer exporterPort) {
        this.exporterPort = exporterPort;
    }

    public String getImporterHost() {
        return importerHost;
    }

    public void setImporterHost(String importerHost) {
        this.importerHost = importerHost;
    }

    public String getImporterPort() {
        return importerPort;
    }

    public void setImporterPort(String importerPort) {
        this.importerPort = importerPort;
    }

    public String getImporterServiceUrl(String serviceName){
        return "rmi://" + importerHost + ":" + importerPort + "/" + serviceName;
    }
}
