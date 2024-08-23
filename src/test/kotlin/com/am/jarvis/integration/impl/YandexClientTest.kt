package com.am.jarvis.integration.impl

import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.Provider
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

private const val TOKEN = "token"
private const val USER_ID = "user-id"

/**
 * Verification for [YandexClient].
 *
 * @author Alex Mikhalochkin
 */
@Disabled("Not Implemented yet")
@ExtendWith(MockKExtension::class)
internal class YandexClientTest {

    @MockK(relaxed = true)
    lateinit var yandexWebClient: WebClient

    @InjectMockKs
    lateinit var yandexClient: YandexClient

    @BeforeEach
    fun init() {
        ReflectionTestUtils.setField(yandexClient, "yandexToken", TOKEN)
        ReflectionTestUtils.setField(yandexClient, "userId", USER_ID)
    }

    @Test
    fun testUpdateStates() {
        val requestBodyUriSpec = mockk<WebClient.RequestBodyUriSpec>()
        every { yandexWebClient.post() } returns requestBodyUriSpec
        val requestBodySpec = mockk<WebClient.RequestBodySpec>()
        every { requestBodyUriSpec.header("Authorization", "OAuth $TOKEN") } returns requestBodySpec
        val slot = slot<YandexNotificationRequest>()
        val requestHeadersSpec = mockk<WebClient.RequestHeadersSpec<*>>()
        every { requestBodySpec.bodyValue(capture(slot)) } returns requestHeadersSpec
        val responseSpec = mockk<WebClient.ResponseSpec>()
        every { requestHeadersSpec.retrieve() } returns responseSpec
        val mono = mockk<Mono<String>>()
        every { responseSpec.bodyToMono(String::class.java) } returns mono
        every { mono.onErrorResume(WebClientResponseException::class.java, any()) } returns mono
        every { mono.block() } returns "ok"
        yandexClient.updateStates(listOf(DeviceState("id", 1, true)))
        val request = slot.captured
        assertNotNull(request.ts)
        val payload = request.payload
        assertEquals(USER_ID, payload.userId)
        val device = payload.devices[0]
        assertEquals("id", device.id)
        val capability = device.capabilities[0]
        assertEquals("on", capability.state.instance)
        assertEquals(true, capability.state.value)
        assertEquals("devices.capabilities.on_off", capability.type)
    }

    @Test
    fun testGetProvider() {
        assertEquals(Provider.YANDEX, yandexClient.getProvider())
    }
}
