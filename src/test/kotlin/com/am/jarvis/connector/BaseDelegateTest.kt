package com.am.jarvis.connector

import com.am.jarvis.generateUuid
import com.am.jarvis.service.api.SmartHomeService
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceName
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Room
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.ConversionService

/**
 * Base test class for delegates.
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
open class BaseDelegateTest<T : Any> {

    @MockK
    lateinit var conversionService: ConversionService

    @MockK
    lateinit var smartHomeService: SmartHomeService

    @InjectMockKs
    lateinit var delegate: T

    val requestId = generateUuid()

    val deviceId = generateUuid()

    @AfterEach
    fun verify() = confirmVerified(conversionService, smartHomeService)

    fun createDevice() = Device(
        deviceId,
        Room("room"),
        DeviceName("device"),
        "description",
        emptyList()
    )

    fun createDeviceState() = DeviceState(deviceId, true, mapOf("port" to 1))
}
