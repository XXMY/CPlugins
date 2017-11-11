package com.cfw.test;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String [] args) throws Exception {
        Service service = new Service();
        Class serviceClass = service.getClass();
        //Method[] serviceMethods = serviceClass.getDeclaredMethods();

        Method serviceMethod = serviceClass.getDeclaredMethod("service",Integer.class,int.class);
        Parameter parameters [] = serviceMethod.getParameters();

        Map<String,Class> parameterTypeMap = new HashMap<String,Class>(parameters.length);
        for(Parameter parameter : parameters){
            parameterTypeMap.put(parameter.getName(),parameter.getType());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a","2");
        jsonObject.put("b","5");
        Object [] parameter = new Object[parameters.length];

        Set<String> keySet = parameterTypeMap.keySet();
        int i = 0;
        for(String key : keySet){
            parameter[i] = (parameterTypeMap.get(key)).cast(jsonObject.get(key));
        }

        serviceMethod.invoke(service,parameter);

    }
}
