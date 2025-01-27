package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
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
@MosquitoEnabled
class MqttConfiguration {

    @Bean
    fun mqttClientListener(
        @Value("\${mqtt.mosquitto.server-url}") mqttServerUrl: String,
        @Value("\${mqtt.mosquitto.listener-client-id}") clientId: String,
        mqttConnectOptions: MqttConnectOptions,
        topicMessageProcessors: List<MqttTopicMessageProcessor>
    ): MqttCallbackExtended {
        val mqttListener = MqttListener(topicMessageProcessors, mqttServerUrl, clientId)
        mqttListener.setCallback(mqttListener)
        mqttListener.connect(mqttConnectOptions)
        return mqttListener
    }

    @Bean
    fun mqttClientPublisher(
        @Value("\${mqtt.mosquitto.server-url}") mqttServerUrl: String,
        @Value("\${mqtt.mosquitto.publisher-client-id}") clientId: String,
        mqttConnectOptions: MqttConnectOptions
    ): MqttClient {
        return CommonMqttClient(mqttServerUrl, clientId).apply {
            connect(mqttConnectOptions)
        }
    }
}
