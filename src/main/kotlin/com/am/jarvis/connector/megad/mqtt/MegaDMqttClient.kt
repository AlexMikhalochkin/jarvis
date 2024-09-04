package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.connector.megad.repository.impl.ConfiguredDevices
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Mqtt client for MegaD.
 *
 * @author Alex Mikhalochkin
 */
@Component
internal class MegaDMqttClient(
    @Value("\${megad.id}") megadId: String,
    @Value("\${mqtt.server-url}") mqttServerUrl: String,
    callback: MqttCallback,
    options: MqttConnectOptions,
    configuredDevices: ConfiguredDevices
) : MqttClient(mqttServerUrl, "jarvis") {

    init {
        this.setCallback(callback)
        this.connect(options)
        val outPorts = configuredDevices.devices
            .map { it.additionalData["port"] as Int }
        val topicFilters = outPorts
            .map { "$megadId/$it" }
            .toTypedArray()
        this.subscribe(topicFilters, IntArray(outPorts.size) { 0 })
    }

    override fun close() {
        if (isConnected) {
            disconnect()
        }
        close(true)
    }
}
