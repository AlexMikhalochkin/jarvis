package com.mega.demo

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
class DemoApplication {

    @Value("\${plc.url}")
    private lateinit var plcUrl: String

    @Value("\${smart.things.url}")
    private lateinit var smartThingsUrl: String

    @Value("\${mqtt.server.url}")
    private lateinit var mqttServerUrl: String

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
    fun client(callback: MqttCallback): IMqttClient {
        val publisherId = "Alex"
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        val mqttClient = MqttClient(mqttServerUrl, publisherId)
        mqttClient.setCallback(callback)
        mqttClient.connect(options)
        mqttClient.subscribe("megad/14/#", 0)
        return mqttClient
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
