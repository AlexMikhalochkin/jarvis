package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [RefreshStoredStatesWorker].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class RefreshStoredStatesWorkerTest {

    @MockK
    private lateinit var deviceRepository: DeviceRepository

    @MockK(relaxUnitFun = true)
    private lateinit var publisher: MegaDMqttCommandMessagePublisher

    @InjectMockKs
    private lateinit var worker: RefreshStoredStatesWorker

    @Test
    fun testRefresh() {
        every { deviceRepository.findAllPorts() } returns listOf(1, 18)

        worker.refresh()

        verify { publisher.publish("get:1") }
        verify { publisher.publish("get:18") }
    }

    @Test
    fun testRefreshNoPortsFound() {
        every { deviceRepository.findAllPorts() } returns emptyList()

        worker.refresh()

        verify { publisher wasNot called }
    }
}
