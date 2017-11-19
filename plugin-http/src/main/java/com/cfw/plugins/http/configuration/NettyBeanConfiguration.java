package com.cfw.plugins.http.configuration;

import com.cfw.plugins.netty.http.response.HttpResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyBeanConfiguration {

    @Bean
    public HttpResponseHandler httpResponseHandler(){
        return new HttpResponseHandler();
    }
}
