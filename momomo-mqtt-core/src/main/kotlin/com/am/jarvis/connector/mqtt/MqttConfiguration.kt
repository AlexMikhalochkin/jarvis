package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
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
            isAutomaticReconnect = true
        }
    }

    @Bean
    @Primary
    fun mqttClient(
        @Value("\${mqtt.mosquitto.server-url}") mqttServerUrl: String,
        mqttConnectOptions: MqttConnectOptions,
        topicMessageProcessors: List<MqttTopicMessageProcessor>
    ): MqttCallbackExtended {
        val mqttListener = MqttListener(topicMessageProcessors, mqttServerUrl)
        mqttListener.setCallback(mqttListener)
        mqttListener.connect(mqttConnectOptions)
        return mqttListener
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
