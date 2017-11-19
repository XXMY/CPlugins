package com.cfw.plugins.http.configuration;

import com.cfw.plugins.netty.http.mapping.ExecutorMapping;
import com.cfw.plugins.netty.http.mapping.RequestPathExecutorMapping;
import com.cfw.plugins.netty.http.request.HttpRequestDataParseHandler;
import com.cfw.plugins.netty.http.request.HttpRequestDispatchHandler;
import com.cfw.plugins.netty.http.response.HttpResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyBeanConfiguration {

    @Bean
    public HttpResponseHandler httpResponseHandler(){
        return new HttpResponseHandler();
    }

    @Bean
    public HttpRequestDataParseHandler httpRequestDataParseHandler(){
        return new HttpRequestDataParseHandler();
    }

    @Autowired
    @Bean
    public HttpRequestDispatchHandler httpRequestDispatchHandler(ExecutorMapping executorMapping){
        return new HttpRequestDispatchHandler(executorMapping);
    }

    @Bean
    public RequestPathExecutorMapping requestPathExecutorMapping(){
        return new RequestPathExecutorMapping();
    }
}
