package com.dream.chat.web.config;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.Servlet;

//@Configuration
public class WebSocketConfig {
    /*@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }*/

    @ConditionalOnClass({ Servlet.class, Tomcat.class })

    @Bean

    public ServerEndpointExporter serverEndpointExporter() {

        return new ServerEndpointExporter();

    }


}
