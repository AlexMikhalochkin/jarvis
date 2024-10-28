package com.am.jarvis.connector.mqtt

import org.eclipse.paho.client.mqttv3.MqttClient

/**
 * Common MQTT client.
 *
 * @author Alex Mikhalochkin
 */
@MosquitoEnabled
internal class CommonMqttClient(
    mqttServerUrl: String
) : MqttClient(mqttServerUrl, "jarvis") {

    override fun close() {
        if (isConnected) {
            disconnect()
        }
        close(true)
    }
}
