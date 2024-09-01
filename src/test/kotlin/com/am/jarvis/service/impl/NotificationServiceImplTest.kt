package com.am.jarvis.service.impl

import com.am.jarvis.generateUuid
import com.am.jarvis.integration.api.SmartHomeProviderClient
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Verification for [NotificationServiceImpl].
 *
 * @author Alex Mikhalochkin
 */
internal class NotificationServiceImplTest {

    private lateinit var service: NotificationServiceImpl
    private lateinit var yandexClient: SmartHomeProviderClient
    private lateinit var smartThingsClient: SmartHomeProviderClient

    @BeforeEach
    fun init() {
        yandexClient = mockk(relaxUnitFun = true)
        smartThingsClient = mockk(relaxUnitFun = true)
        service = NotificationServiceImpl(listOf(yandexClient, smartThingsClient))
    }

    @Test
    fun testNotifyProviders() {
        val states = listOf(DeviceState(generateUuid(), true, mapOf("port" to 1)))
        every { yandexClient.getProvider() } returns Provider.YANDEX
        every { smartThingsClient.getProvider() } returns Provider.SMART_THINGS
        service.notifyProviders(states, Provider.SMART_THINGS)
        verifySequence {
            yandexClient.getProvider()
            smartThingsClient.getProvider()
            yandexClient.updateStates(states)
        }
    }

    @Test
    fun testNotifyAllProviders() {
        val states = listOf(DeviceState(generateUuid(), true, mapOf("port" to 1)))
        service.notifyProviders(states)
        verifySequence {
            yandexClient.updateStates(states)
            smartThingsClient.updateStates(states)
        }
    }
}
