package com.am.jarvis.connector.megad.http

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Tests for [MegaDHttpService].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class MegaDHttpServiceTest {

    @MockK
    private lateinit var megaDHttpClient: MegaDHttpClient

    @MockK
    private lateinit var deviceRepository: DeviceRepository

    @InjectMockKs
    private lateinit var service: MegaDHttpService

    @Test
    fun getStatesForAllPorts() {
        val expected = mapOf(1 to true, 3 to false, 4 to false, 11 to false)
        every { deviceRepository.findAllPorts() } returns listOf(1, 3, 4, 11)
        every { megaDHttpClient.getStatesForAllPorts() } returns "0=1;1;0/0;0;2;432"

        val statesForAllPorts = service.getStatesForAllPorts()

        assertEquals(expected, statesForAllPorts)
    }
}
