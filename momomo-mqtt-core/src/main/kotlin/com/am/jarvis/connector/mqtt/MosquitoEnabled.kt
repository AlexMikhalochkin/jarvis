package com.am.jarvis.connector.mqtt

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

/**
 * Enables connection to the Mosquito MQTT broker.
 *
 * @author Alex Mikhalochkin
 */
@ConditionalOnProperty(name = ["mqtt.active"], havingValue = "mosquitto")
annotation class MosquitoEnabled
