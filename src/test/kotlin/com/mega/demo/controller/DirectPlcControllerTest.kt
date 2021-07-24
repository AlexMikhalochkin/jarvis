package com.mega.demo.controller

import com.mega.demo.service.api.PlcService
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono

/**
 * Verifies [DirectPlcController].
 *
 * @author Alex Mikhalochkin
 */
internal class DirectPlcControllerTest {

    private lateinit var controller: DirectPlcController
    private lateinit var plcService: PlcService

    @BeforeEach
    fun init() {
        plcService = mock()
        controller = DirectPlcController(plcService)
    }

    @Test
    fun testTurnOn() {
        controller.turnOn()
        verify(plcService).turnOn(7)
    }

    @Test
    fun testGetStatuses() {
        val response = Mono.just(emptyMap<Int, String>())
        whenever(plcService.getPortStatuses()).thenReturn(response)
        assertSame(response, controller.getStatuses())
        verify(plcService).getPortStatuses()
    }
}
