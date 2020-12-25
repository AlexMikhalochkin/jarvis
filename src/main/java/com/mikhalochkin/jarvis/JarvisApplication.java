package com.mikhalochkin.jarvis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class JarvisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JarvisApplication.class, args);
    }

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
