package com.ayoub.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        //?configure webflux security details
        serverHttpSecurity.csrf()
                        .disable()
                        .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**")//allow only this without auth
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                        .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return serverHttpSecurity.build();


    }
}
