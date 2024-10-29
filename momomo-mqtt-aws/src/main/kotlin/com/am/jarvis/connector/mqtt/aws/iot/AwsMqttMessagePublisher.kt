package com.am.jarvis.connector.mqtt.aws.iot

import com.am.jarvis.core.api.MqttMessagePublisher
import org.springframework.stereotype.Service
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.MqttMessage
import software.amazon.awssdk.crt.mqtt.QualityOfService

/**
 * An implementation of [MqttMessagePublisher] that publishes messages to AWS IoT Core.
 *
 * @author Alex Mikhalochkin
 */
@Service
@AwsIotEnabled
class AwsMqttMessagePublisher(
    private val mqttClientConnection: MqttClientConnection
) : MqttMessagePublisher {

    override fun publish(topic: String, message: String) {
        val mqttMessage = MqttMessage(topic, message.toByteArray(), QualityOfService.AT_MOST_ONCE, true)
        mqttClientConnection.publish(mqttMessage).get()
    }
}
