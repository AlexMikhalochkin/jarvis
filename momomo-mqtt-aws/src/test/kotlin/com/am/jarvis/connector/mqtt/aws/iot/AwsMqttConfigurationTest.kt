package com.am.jarvis.connector.mqtt.aws.iot

import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import software.amazon.awssdk.crt.auth.credentials.DefaultChainCredentialsProvider
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import java.util.concurrent.CompletableFuture

/**
 * Verification for [AwsMqttConfiguration]
 *
 * @author Alex Mikhalochkin
 */
class AwsMqttConfigurationTest {

    private val configuration = AwsMqttConfiguration()

    @Test
    fun credentialsProvider() {
        val credentialsProvider = configuration.credentialsProvider()

        assertEquals(DefaultChainCredentialsProvider::class, credentialsProvider::class)
    }

    @Test
    fun mqttClientConnection() {
        val mqttClientConnection = configuration.mqttClientConnection(
            configuration.credentialsProvider(),
            "endpoint",
            "region",
            "clientId",
            8883
        )
        assertEquals(MqttClientConnection::class, mqttClientConnection::class)
    }

    @Test
    fun connect() {
        val mqttClientConnection = mockkClass(MqttClientConnection::class)
        every { mqttClientConnection.connect() } returns CompletableFuture.completedFuture(null)

        configuration.connect(mqttClientConnection)

        verify { mqttClientConnection.connect() }
    }
}
