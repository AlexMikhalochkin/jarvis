package com.mega.demo.integration

import org.apache.activemq.broker.BrokerService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.JmsListenerContainerFactory
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.web.reactive.function.client.WebClient


/**
 * TODO: add description
 *
 * @author Alex Mikhalochkin
 */
@EnableJms
@Configuration
class IntegrationConfiguration {

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

    @Bean
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
    fun broker(): BrokerService {
        val broker = BrokerService()
        broker.addConnector("tcp://0.0.0.0:61616")
        return broker
    }
}