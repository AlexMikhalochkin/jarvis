package com.mega.demo.controller

import com.mega.demo.controller.model.yandex.Device
import com.mega.demo.controller.model.yandex.Payload
import com.mega.demo.controller.model.yandex.YandexChangeStateRequest
import com.mega.demo.controller.model.yandex.YandexRequest
import com.mega.demo.controller.model.yandex.YandexResponse
import com.mega.demo.generateUuid
import com.mega.demo.service.api.YandexService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Verification for [YandexController].
 *
 * @author Alex Mikhalochkin
 */
internal class YandexControllerTest {

    private val requestId = generateUuid()
    private val deviceId = generateUuid()
    private val devices = listOf(Device(deviceId))

    private lateinit var controller: YandexController
    private lateinit var service: YandexService

    @BeforeEach
    fun init() {
        service = mock()
        controller = YandexController(service)
    }

    @Test
    fun testHealth() {
        controller.health()
    }

    @Test
    fun testUnlink() {
        assertEquals(mapOf("request_id" to requestId), controller.unlink(requestId))
    }

    @Test
    fun testGetDevices() {
        whenever(service.getAllDevices()).thenReturn(devices)
        val response = controller.getDevices(requestId)
        verifyResponse(response, devices, "user-id")
    }

    @Test
    fun testRefreshDevices() {
        whenever(service.getDeviceStates(any())).thenReturn(devices)
        val response = controller.refreshDevices(requestId, YandexRequest(devices))
        verifyResponse(response, devices)
        verify(service).getDeviceStates(listOf(deviceId))
    }

    @Test
    fun testChangeDevicesStates() {
        whenever(service.changeState(any())).thenReturn(devices)
        val response = controller.changeDevicesStates(requestId, YandexChangeStateRequest(Payload(devices)))
        verifyResponse(response, devices)
        verify(service).changeState(devices)
    }

    private fun verifyResponse(
        response: YandexResponse,
        expectedDevices: List<Device>,
        expectedUserId: String? = null
    ) {
        assertEquals(requestId, response.requestId)
        val payload = response.payload
        assertEquals(expectedUserId, payload.userId)
        assertEquals(expectedDevices, payload.devices)
    }
}
