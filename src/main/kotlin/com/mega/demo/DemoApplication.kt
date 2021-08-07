package com.mega.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class DemoApplication {

    @Value("\${plc.url}")
    private lateinit var plcUrl: String

    /**
     * WebClient for MegaD controller.
     *
     * @return instance of [WebClient].
     */
    @Bean
    fun webClient(): WebClient {
        return WebClient.create(plcUrl)
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
