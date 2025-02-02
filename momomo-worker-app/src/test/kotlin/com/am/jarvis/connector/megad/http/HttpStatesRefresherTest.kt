package com.am.jarvis.connector.megad.http

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.core.model.Room
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Tests for [HttpStatesRefresher].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class HttpStatesRefresherTest {

    @MockK
    private lateinit var deviceRepository: DeviceRepository

    @MockK
    private lateinit var megaDHttpService: MegaDHttpService

    @MockK(relaxUnitFun = true)
    private lateinit var notifiers: Notifier

    private lateinit var refresher: HttpStatesRefresher

    @BeforeEach
    fun setUp() {
        refresher = HttpStatesRefresher(deviceRepository, megaDHttpService, listOf(notifiers))
    }

    @Test
    fun run() {
        every { megaDHttpService.getStatesForAllPorts() } returns mapOf(1 to true, 2 to false)
        every { deviceRepository.findAll() } returns listOf(
            createDevice("1", mapOf("port" to 1)),
            createDevice("2", mapOf("port" to 2))
        )

        refresher.run()

        verify { notifiers.notify(listOf(DeviceState("1", true), DeviceState("2", false))) }
    }

    private fun createDevice(deviceId: String, additionalData: Map<String, Any>) = Device(
        deviceId,
        Room("room"),
        DeviceName("device"),
        "description",
        emptyList(),
        "MegaD",
        additionalData
    )
}
