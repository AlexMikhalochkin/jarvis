package com.am.jarvis.connector.mqtt

import org.eclipse.paho.client.mqttv3.MqttClient

/**
 * Common MQTT client.
 *
 * @author Alex Mikhalochkin
 */
internal class CommonMqttClient(
    mqttServerUrl: String,
    clientId: String
) : MqttClient(mqttServerUrl, clientId) {

    override fun close() {
        if (isConnected) {
            disconnect()
        }
        close(true)
    }
}
