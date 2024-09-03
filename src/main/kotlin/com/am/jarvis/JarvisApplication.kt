package com.am.jarvis

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class JarvisApplication {

    private val maxPayloadLength = 10000

    /**
     * WebClient for SmartThings callbacks.
     *
     * @return instance of [WebClient].
     */
    @Bean("smartThingsWebClient")
    fun smartThingsWebClient(@Value("\${smart-things.url}") smartThingsUrl: String): WebClient {
        return WebClient.create(smartThingsUrl)
    }

    /**
     * WebClient for Yandex callbacks.
     *
     * @return instance of [WebClient].
     */
    @Bean("yandexWebClient")
    fun yandexWebClient(@Value("\${yandex.notification-url}") yandexUrl: String): WebClient {
        return WebClient.create(yandexUrl)
    }

    @Bean
    fun logFilter(): CommonsRequestLoggingFilter {
        val filter = CommonsRequestLoggingFilter()
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(maxPayloadLength)
        filter.setIncludeHeaders(true)
        filter.setAfterMessagePrefix("REQUEST DATA: ")
        return filter
    }
}

fun main(args: Array<String>) {
    runApplication<JarvisApplication>(*args)
}
