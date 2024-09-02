package com.am.jarvis.connector.yandex.notifier

import com.am.momomo.model.DeviceState
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

/**
 * Verification for [YandexNotifier].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class YandexNotifierTest {

    private val token = "token"
    private val userId = "user-id"

    @MockK(relaxed = true)
    lateinit var yandexWebClient: WebClient

    private lateinit var yandexNotifier: YandexNotifier

    @BeforeEach
    fun init() {
        yandexNotifier = YandexNotifier(yandexWebClient, token, userId)
    }

    @Test
    fun testUpdateStates() {
        val requestBodyUriSpec = mockk<WebClient.RequestBodyUriSpec>()
        every { yandexWebClient.post() } returns requestBodyUriSpec
        val requestBodySpec = mockk<WebClient.RequestBodySpec>()
        every { requestBodyUriSpec.header("Authorization", "OAuth $token") } returns requestBodySpec
        val slot = slot<YandexNotificationRequest>()
        val requestHeadersSpec = mockk<WebClient.RequestHeadersSpec<*>>()
        every { requestBodySpec.bodyValue(capture(slot)) } returns requestHeadersSpec
        val responseSpec = mockk<WebClient.ResponseSpec>()
        every { requestHeadersSpec.retrieve() } returns responseSpec
        val mono = mockk<Mono<String>>()
        every { responseSpec.bodyToMono(String::class.java) } returns mono
        every { mono.onErrorResume(WebClientResponseException::class.java, any()) } returns mono
        every { mono.block() } returns "ok"
        yandexNotifier.notify(listOf(DeviceState("id", true, mapOf("port" to 1))))
        val request = slot.captured
        assertNotNull(request.ts)
        val payload = request.payload
        assertEquals(userId, payload.userId)
        val device = payload.devices[0]
        assertEquals("id", device.id)
        val capability = device.capabilities[0]
        assertEquals("on", capability.state.instance)
        assertEquals(true, capability.state.value)
        assertEquals("devices.capabilities.on_off", capability.type)
    }

    @Test
    fun testGetProvider() {
        assertEquals("YANDEX", yandexNotifier.getSource())
    }
}
