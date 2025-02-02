package com.am.jarvis.connector.mqtt.aws.iot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.crt.auth.credentials.CredentialsProvider
import software.amazon.awssdk.crt.auth.credentials.DefaultChainCredentialsProvider
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder

@Configuration
@AwsIotEnabled
class AwsMqttConfiguration {

    @Bean
    fun credentialsProvider(): CredentialsProvider {
        return DefaultChainCredentialsProvider.DefaultChainCredentialsProviderBuilder().build()
    }

    @Bean
    fun mqttClientConnection(
        credentialsProvider: CredentialsProvider,
        @Value("\${mqtt.aws.endpoint}") endpoint: String,
        @Value("\${mqtt.aws.region}") region: String,
        @Value("\${mqtt.aws.client-id}") clientId: String,
        @Value("\${mqtt.aws.port}") port: Int
    ): MqttClientConnection {
        val connection = AwsIotMqttConnectionBuilder.newMtlsBuilderFromPath(null, null)
            .withEndpoint(endpoint)
            .withPort(port)
            .withClientId(clientId)
            .withWebsockets(true)
            .withWebsocketSigningRegion(region)
            .withWebsocketCredentialsProvider(credentialsProvider)
            .build()
        return connection
    }

    @Bean
    fun connect(mqttClientConnection: MqttClientConnection) {
        mqttClientConnection.connect().get()
    }
}
