package com.am.jarvis.connector.yandex.notifier

import com.am.jarvis.connector.yandex.notifier.converter.StateConverter
import com.am.jarvis.controller.generated.model.ChangeStatesDevice
import com.am.jarvis.controller.generated.model.YandexNotificationRequest
import com.am.jarvis.core.model.DeviceState
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
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

    @MockK
    lateinit var converter: StateConverter

    private lateinit var yandexNotifier: YandexNotifier

    @BeforeEach
    fun init() {
        yandexNotifier = YandexNotifier(yandexApiClient, converter, userId)
    }

    @Test
    fun testUpdateStates() {
        val slot = slot<YandexNotificationRequest>()
        val deviceId = "id"
        val changeStatesDevice = ChangeStatesDevice(deviceId, listOf(), listOf())

        every { converter.convert(any()) } returns changeStatesDevice

        yandexNotifier.notify(listOf(DeviceState(deviceId, false)))

        verify { yandexApiClient.notify(capture(slot)) }
        val request = slot.captured
        assertNotNull(request.ts)
        val payload = request.payload
        assertSame(userId, payload.userId)
        assertSame(changeStatesDevice, payload.devices[0])

//        assertEquals("id", device.id)
//        val capability = device.capabilities[0]
//        assertEquals("on", capability.state.instance)
//        assertEquals(true, capability.state.value)
//        assertEquals("devices.capabilities.on_off", capability.type)
    }

    @Test
    fun testGetProvider() {
        assertEquals("YANDEX", yandexNotifier.getSource())
    }
}
