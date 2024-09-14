package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.SmartThingsDeviceState
import com.am.jarvis.core.model.DeviceState
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.ConversionService

/**
 * Verification for [SmartThingsNotifier].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class SmartThingsNotifierTest {

    @MockK(relaxUnitFun = true)
    private lateinit var smartThingsApiClient: SmartThingsApiClient

    @MockK
    private lateinit var tokenService: SmartThingsTokenService

    @MockK
    private lateinit var conversionService: ConversionService

    @InjectMockKs
    private lateinit var notifier: SmartThingsNotifier

    @Test
    fun testNotify() {
        val states = listOf(DeviceState("device1", true), DeviceState("device2", false))
        val callbackUrl = "http://test.test/test"
        val callbackState = SmartThingsDeviceState()
        every { tokenService.getAccessToken() } returns "token"
        every { conversionService.convert(states[0], SmartThingsDeviceState::class.java) } returns callbackState
        every { conversionService.convert(states[1], SmartThingsDeviceState::class.java) } returns callbackState
        every { tokenService.getCallbackUrl() } returns callbackUrl

        notifier.notify(states)

        verify { smartThingsApiClient.sendCallback(any(), callbackUrl) }
    }

    @Test
    fun getSource() {
        assertEquals("SMART_THINGS", notifier.getSource())
    }
}
