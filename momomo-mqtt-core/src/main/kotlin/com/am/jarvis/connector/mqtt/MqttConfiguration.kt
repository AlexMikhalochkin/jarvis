package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * Configuration for MQTT.
 *
 * @author Alex Mikhalochkin
 */
@Configuration
@MosquitoEnabled
class MqttConfiguration {

    @Bean
    fun mqttConnectOptions(
        @Value("\${mqtt.mosquitto.username}") mqttBrokerUsername: String,
        @Value("\${mqtt.mosquitto.password}") mqttBrokerPassword: String
    ): MqttConnectOptions {
        return MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
            password = mqttBrokerPassword.toCharArray()
            userName = mqttBrokerUsername
        }
    }

    @Bean
    @Primary
    fun mqttClient(
        @Value("\${mqtt.mosquitto.server-url}") mqttServerUrl: String,
        mqttCallback: MqttCallback,
        mqttConnectOptions: MqttConnectOptions,
        topicMessageProcessors: List<MqttTopicMessageProcessor>
    ): MqttClient {
        val topics = topicMessageProcessors.flatMap { it.getSupportedTopics() }
            .toTypedArray()
        return CommonMqttClient(mqttServerUrl).apply {
            setCallback(mqttCallback)
            connect(mqttConnectOptions)
            subscribe(topics, IntArray(topics.size) { 0 })
        }
    }

    @Bean("mqttClientPublisher")
    fun mqttClientPublisher(
        @Value("\${mqtt.mosquitto.server-url}") mqttServerUrl: String,
        mqttConnectOptions: MqttConnectOptions
    ): MqttClient {
        return MqttClient(mqttServerUrl, "jarvis2").apply {
            connect(mqttConnectOptions)
        }
    }
}
