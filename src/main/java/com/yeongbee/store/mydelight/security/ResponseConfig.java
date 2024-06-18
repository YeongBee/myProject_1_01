package com.yeongbee.store.mydelight.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResponseConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
