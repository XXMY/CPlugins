package com.cfw.plugins.netty.http.configuration;

import com.cfw.plugins.netty.http.response.HttpResponseHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyBeanConfiguration {

    @Bean
    public HttpResponseHandler httpResponseHandler(){
        return new HttpResponseHandler();
    }
}
