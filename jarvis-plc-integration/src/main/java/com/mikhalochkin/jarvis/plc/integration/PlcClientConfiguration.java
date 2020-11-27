package com.mikhalochkin.jarvis.plc.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for plc-integration module.
 *
 * @author Alex Mikhalochkin
 */
@Configuration
@ComponentScan("com.mikhalochkin.jarvis.plc.integration.impl")
public class PlcClientConfiguration {

    /**
     * WebClient for MegaD controller.
     *
     * @param megaDUrl url of the MegaD controller.
     * @return instance of {@link WebClient}.
     */
    @Bean
    public WebClient webClient(@Value("${plc.megad.url}") String megaDUrl) {
        return WebClient.create(megaDUrl);
    }
}
