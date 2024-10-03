package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration for MQTT.
 *
 * @author Alex Mikhalochkin
 */
@Configuration
class MqttConfiguration {

    @Bean
    fun mqttConnectOptions(
        @Value("\${mqtt.broker.username}") mqttBrokerUsername: String,
        @Value("\${mqtt.broker.password}") mqttBrokerPassword: String
    ): MqttConnectOptions {
        return MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
            password = mqttBrokerPassword.toCharArray()
            userName = mqttBrokerUsername
        }
    }

    @Bean
    fun mqttClient(
        @Value("\${mqtt.server-url}") mqttServerUrl: String,
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
}
