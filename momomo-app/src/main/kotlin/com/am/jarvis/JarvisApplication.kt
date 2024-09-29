package com.am.jarvis

import com.am.jarvis.core.model.RetryableServerException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry
import reactor.util.retry.RetryBackoffSpec
import java.time.Duration

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class JarvisApplication {

    private val maxPayloadLength = 10000

    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }

    @Bean
    fun retryBackoffSpec(
        @Value("\${client.retry.max-attempts}") maxAttempts: Long,
        @Value("\${client.retry.delay-millis}") delayMillis: Long
    ): RetryBackoffSpec {
        return Retry.backoff(maxAttempts, Duration.ofMillis(delayMillis))
            .filter { it is RetryableServerException }
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
