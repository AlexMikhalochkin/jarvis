package com.mikhalochkin.jarvis.plc.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("com.mikhalochkin.jarvis.plc.integration.impl")
public class PlcClientConfiguration {

    @Bean
    public WebClient webClient(@Value("${plc.megad.url}") String megaDUrl) {
        return WebClient.create(megaDUrl);
    }
}
