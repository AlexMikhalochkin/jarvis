package com.am.jarvis.connector.mqtt

import jakarta.annotation.PostConstruct
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Publisher for MQTT topics.
 *
 * @author Alex Mikhalochkin
 */
@Qualifier("mqttClientPublisher")
@Component
@MosquitoEnabled
class MqttPublisher(
    @Value("\${mqtt.mosquitto.server-url}") mqttServerUrl: String,
    private val mqttConnectOptions: MqttConnectOptions
) : MqttClient(mqttServerUrl, "jarvis-publisher") {

    @PostConstruct
    fun init() {
        connect(mqttConnectOptions)
    }
}
