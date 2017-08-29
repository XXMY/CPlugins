package com.cfw.plugins.mq.rabbitmq.rpc;

import java.io.Serializable;

/**
 * Created by Duskrain on 2017/8/27.
 */
public class RemoteProcedureResponse implements Serializable{
    private static final long serialVersionUID = -6929208142808802505L;
    private String requestId;
    private Object result;
    private Exception exception;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getE() {
        return exception;
    }

    public void setE(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "RemoteProcedureResponse{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                ", exception=" + exception +
                '}';
    }
}
