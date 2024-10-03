package com.am.jarvis.core.api

/**
 * Interface representing an MQTT message publisher.
 * Implementations of this interface should provide logic to publish messages to specified MQTT topics.
 *
 *  @author Alex Mikhalochkin
 */
interface MqttMessagePublisher {

    /**
     * Publishes a message to the given MQTT topic.
     *
     * @param topic The MQTT topic to publish the message to.
     * @param message The message to publish.
     */
    fun publish(topic: String, message: String)
}
