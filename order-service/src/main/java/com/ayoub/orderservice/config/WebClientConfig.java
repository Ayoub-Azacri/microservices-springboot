package com.ayoub.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //?Define a bean type of class
    @Bean
    @LoadBalanced //? even if webclient fins multiple inventoryServices it'll load them
                //?one by one it won't be confused
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
