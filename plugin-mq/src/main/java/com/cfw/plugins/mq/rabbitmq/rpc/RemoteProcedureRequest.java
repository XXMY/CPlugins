package com.cfw.plugins.mq.rabbitmq.rpc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Duskrain on 2017/8/27.
 */
public class RemoteProcedureRequest implements Serializable{

    private static final long serialVersionUID = 7199371550206241555L;

    private String service;

    private String method;

    private List<Object> data;

    private String requetId;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getRequetId() {
        return requetId;
    }

    public void setRequetId(String requetId) {
        this.requetId = requetId;
    }

    @Override
    public String toString() {
        return "RemoteProcedureRequest{" +
                "service='" + service + '\'' +
                ", method='" + method + '\'' +
                ", data=" + data +
                ", requetId='" + requetId + '\'' +
                '}';
    }
}
