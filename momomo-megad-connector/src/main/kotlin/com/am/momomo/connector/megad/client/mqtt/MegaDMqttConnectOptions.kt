package com.am.momomo.connector.megad.client.mqtt

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Mqtt broker connection options.
 *
 * @author Alex Mikhalochkin
 */
@Component
internal class MegaDMqttConnectOptions(
    @Value("\${mqtt.broker.username}") mqttBrokerUsername: String,
    @Value("\${mqtt.broker.password}") mqttBrokerPassword: String
) : MqttConnectOptions() {

    init {
        isAutomaticReconnect = true
        isCleanSession = true
        password = mqttBrokerPassword.toCharArray()
        userName = mqttBrokerUsername
    }
}
