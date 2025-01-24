package com.am.jarvis.connector.mqtt

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@MosquitoEnabled
class CommonMqttConnectOptions(
    @Value("\${mqtt.mosquitto.username}") mqttBrokerUsername: String,
    @Value("\${mqtt.mosquitto.password}") mqttBrokerPassword: String
) : MqttConnectOptions() {

    init {
        isAutomaticReconnect = true
        isCleanSession = true
        password = mqttBrokerPassword.toCharArray()
        userName = mqttBrokerUsername
        isAutomaticReconnect = true
    }
}
