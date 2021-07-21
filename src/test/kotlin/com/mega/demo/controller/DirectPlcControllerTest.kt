package com.mega.demo.controller

import com.mega.demo.integration.MegaDClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono

internal class DirectPlcControllerTest {

    @Test
    fun testTurnOn() {
        val megaDClient : MegaDClient = mock()
        val controller = DirectPlcController(megaDClient)
        controller.turnOn()
        verify(megaDClient).turnOn(7)
    }

    @Test
    fun testGetStatuses() {
        val megaDClient : MegaDClient = mock()
        val controller = DirectPlcController(megaDClient)
        val response = Mono.just(emptyMap<Int, String>())
        whenever(megaDClient.getPortStatuses()).thenReturn(response)
        assertEquals(response, controller.getStatuses())
        verify(megaDClient).getPortStatuses()
    }
}
