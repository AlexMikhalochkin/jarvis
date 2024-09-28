package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.core.model.DeviceState
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [YandexNotifier].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class YandexNotifierTest {

    private val userId = "user-id"

    @MockK(relaxed = true)
    lateinit var yandexApiClient: YandexApiClient

    private lateinit var yandexNotifier: YandexNotifier

    @BeforeEach
    fun init() {
        yandexNotifier = YandexNotifier(yandexApiClient, userId)
    }

    @Test
    fun testUpdateStates() {
        val slot = slot<YandexNotificationRequest>()

        yandexNotifier.notify(listOf(DeviceState("id", true, mapOf("port" to 1))))

        verify { yandexApiClient.notify(capture(slot)) }
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
