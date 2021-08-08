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

    @Value("\${smart.things.url}")
    private lateinit var smartThingsUrl: String

    /**
     * WebClient for MegaD controller.
     *
     * @return instance of [WebClient].
     */
    @Bean("megaDWebClient")
    fun megaDWebClient(): WebClient {
        return WebClient.create(plcUrl)
    }

    /**
     * WebClient for SmartThings callbacks.
     *
     * @return instance of [WebClient].
     */
    @Bean("smartThingsWebClient")
    fun smartThingsWebClient(): WebClient {
        return WebClient.create(smartThingsUrl)
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
