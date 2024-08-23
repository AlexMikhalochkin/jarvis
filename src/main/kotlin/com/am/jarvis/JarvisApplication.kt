package com.am.jarvis

import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class JarvisApplication {

    private val outPorts = setOf(7, 8, 9, 10, 11, 12, 13, 22, 23, 24, 25, 26, 27, 28)

    @Value("\${smart.things.url}")
    private lateinit var smartThingsUrl: String

    @Value("\${yandex.notification.url}")
    private lateinit var yandexUrl: String

    @Value("\${mqtt.server.url}")
    private lateinit var mqttServerUrl: String

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

    @Bean
    fun mqttClient(
        @Value("\${megad.id}") megadId: String,
        @Value("\${mqtt.server.url}") mqttServerUrl: String,
        callback: MqttCallback,
        options: MqttConnectOptions
    ): IMqttClient {
        val publisherId = "jarvis"
        val mqttClient = MqttClient(mqttServerUrl, publisherId)
        mqttClient.setCallback(callback)
        mqttClient.connect(options)
        val topicFilters = outPorts
            .map { "$megadId/$it" }
            .toTypedArray()
        mqttClient.subscribe(topicFilters, IntArray(outPorts.size) { 0 })
        return mqttClient
    }

    @Bean
    fun mqttConnectOptions(
        @Value("\${mqtt.broker.username}") mqttBrokerUsername: String,
        @Value("\${mqtt.broker.password}") mqttBrokerPassword: String
    ): MqttConnectOptions {
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        options.password = mqttBrokerPassword.toCharArray()
        options.userName = mqttBrokerUsername
        return options
    }
}

fun main(args: Array<String>) {
    runApplication<JarvisApplication>(*args)
}
