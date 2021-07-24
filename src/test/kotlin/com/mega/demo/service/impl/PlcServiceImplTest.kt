package com.mega.demo.service.impl

import com.mega.demo.integration.api.PlcClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class PlcServiceImplTest {

    private lateinit var service: PlcServiceImpl
    private lateinit var plcClient: PlcClient

    @BeforeEach
    fun init() {
        plcClient = mock()
        service = PlcServiceImpl(plcClient)
    }

    @Test
    fun turnOn() {
        service.turnOn(1)
        verify(plcClient).turnOn(1)
    }

    @Test
    fun turnOff() {
        service.turnOff(1)
        verify(plcClient).turnOff(1)
    }

    @Test
    fun getPortStatuses() {
        service.getPortStatuses()
        verify(plcClient).getPortStatuses()
    }
}
