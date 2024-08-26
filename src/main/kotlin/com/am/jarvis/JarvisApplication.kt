package com.am.jarvis

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class JarvisApplication {

    @Value("\${smart.things.url}")
    private lateinit var smartThingsUrl: String

    @Value("\${yandex.notification.url}")
    private lateinit var yandexUrl: String

    /**
     * WebClient for SmartThings callbacks.
     *
     * @return instance of [WebClient].
     */
    @Bean("smartThingsWebClient")
    fun smartThingsWebClient(): WebClient {
        return WebClient.create(smartThingsUrl)
    }

    /**
     * WebClient for Yandex callbacks.
     *
     * @return instance of [WebClient].
     */
    @Bean("yandexWebClient")
    fun yandexWebClient(): WebClient {
        return WebClient.create(yandexUrl)
    }
}

fun main(args: Array<String>) {
    runApplication<JarvisApplication>(*args)
}
