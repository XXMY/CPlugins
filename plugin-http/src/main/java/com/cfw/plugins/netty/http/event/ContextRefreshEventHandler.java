package com.cfw.plugins.netty.http.event;

import com.cfw.plugins.netty.http.annotation.RequestPath;
import com.cfw.plugins.netty.http.mapping.RequestPathExecutorMapping;
import com.cfw.plugins.netty.http.mapping.RequestPathMappedExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ContextRefreshEventHandler {

    private RequestPathExecutorMapping executorMapping;

    @Autowired
    public ContextRefreshEventHandler(RequestPathExecutorMapping executorMapping){
        this.executorMapping = executorMapping;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @EventListener
    public void contextRefreshedEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() != null)
            return ;

        this.initRequestPathExecutorMapping(event.getApplicationContext());
    }

    /**
     * @param applicationContext
     * @author CaiFangwei
     * @since 2017-11-11 11:05:36
     */
    private void initRequestPathExecutorMapping(ApplicationContext applicationContext){
        Map<String,Object> controllerMap =  applicationContext.getBeansWithAnnotation(RequestPath.class);
        if(controllerMap == null || controllerMap.size() == 0)
            return ;

        Set<String> keySet = controllerMap.keySet();
        try{
            for(String controllerBeanName : keySet){
                Object controller = controllerMap.get(controllerBeanName);
                Class controllerClass = controller.getClass();

                RequestPath controllerRequestPath =  (RequestPath) controllerClass.getAnnotation(RequestPath.class);
                Method controllerMethods [] = controllerClass.getDeclaredMethods();
                for(Method method : controllerMethods){
                    method.setAccessible(true);

                    RequestPath methodRequestPath =  method.getAnnotation(RequestPath.class);
                    if(methodRequestPath == null)
                        continue;

                    Parameter parameters [] = method.getParameters();
                    ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
                    String [] parameterNames = parameterNameDiscoverer.getParameterNames(method);

                    Map<String,Class> parameterTypeMap = null;
                    if(parameters.length > 0){
                        parameterTypeMap = new HashMap<String,Class>(parameters.length);
                        for(int i=0;i<parameters.length;i++){
                            parameterTypeMap.put(parameterNames[i], parameters[i].getType());
                        }
                    }

                    RequestPathMappedExecutor executor = new RequestPathMappedExecutor(controller,method,parameterTypeMap,method.getReturnType());
                    this.executorMapping.addExecutor(controllerRequestPath.value() + methodRequestPath.value(),executor);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
