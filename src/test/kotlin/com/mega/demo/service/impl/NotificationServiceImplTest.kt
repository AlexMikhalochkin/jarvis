package com.mega.demo.service.impl

import com.mega.demo.generateUuid
import com.mega.demo.integration.api.SmartHomeProviderClient
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
        yandexClient = mock()
        smartThingsClient = mock()
        service = NotificationServiceImpl(listOf(yandexClient, smartThingsClient))
    }

    @Test
    fun testNotifyProviders() {
        val states = listOf(DeviceState(generateUuid(), 1, true))
        whenever(yandexClient.getProvider()).thenReturn(Provider.YANDEX)
        whenever(smartThingsClient.getProvider()).thenReturn(Provider.SMART_THINGS)
        service.notifyProviders(states, Provider.SMART_THINGS)
        verify(yandexClient).getProvider()
        verify(smartThingsClient).getProvider()
        verify(yandexClient).updateStates(states)
        verify(smartThingsClient, never()).updateStates(states)
    }

    @Test
    fun testNotifyAllProviders() {
        val states = listOf(DeviceState(generateUuid(), 1, true))
        service.notifyProviders(states)
        verify(yandexClient, never()).getProvider()
        verify(smartThingsClient, never()).getProvider()
        verify(yandexClient).updateStates(states)
        verify(smartThingsClient).updateStates(states)
    }
}
