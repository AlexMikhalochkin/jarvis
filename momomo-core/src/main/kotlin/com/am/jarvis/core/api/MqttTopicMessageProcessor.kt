package com.am.jarvis.core.api

/**
 * Interface representing a processor for MQTT topic messages.
 * Implementations of this interface should provide logic to process messages
 * and specify the topics they support.
 *
 * @author Alex Mikhalochkin
 */
interface MqttTopicMessageProcessor {

    /**
     * Processes the given MQTT message.
     *
     * @param message The MQTT message to process.
     */
    fun process(message: ByteArray)

    /**
     * Returns a list of supported MQTT topics.
     *
     * @return A list of supported topics.
     */
    fun getSupportedTopics(): List<String>
}
