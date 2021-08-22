package com.mega.demo

import org.apache.activemq.broker.BrokerService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.JmsListenerContainerFactory
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.web.reactive.function.client.WebClient

@EnableJms
@SpringBootApplication
class DemoApplication {

    @Value("\${plc.url}")
    private lateinit var plcUrl: String

    @Value("\${smart.things.url}")
    private lateinit var smartThingsUrl: String

    @Value("\${spring.activemq.broker-url}")
    private lateinit var queueUrl: String

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

    @Bean
    @Profile("consumer")
    fun queueListenerFactory(): JmsListenerContainerFactory<*>? {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setMessageConverter(messageConverter())
        return factory
    }

    @Bean
    fun messageConverter(): MessageConverter {
        val converter = MappingJackson2MessageConverter()
        converter.setTargetType(MessageType.TEXT)
        converter.setTypeIdPropertyName("_type")
        return converter
    }

    @Bean
    @Profile("!consumer")
    fun broker(): BrokerService {
        val broker = BrokerService()
        broker.addConnector(queueUrl)
        return broker
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
