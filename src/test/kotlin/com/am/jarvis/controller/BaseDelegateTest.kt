package com.am.jarvis.controller

import com.am.jarvis.generateUuid
import com.am.jarvis.model.Device
import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.TechnicalInfo
import com.am.jarvis.service.api.SmartHomeService
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
        1,
        emptyMap(),
        emptyMap(),
        emptyMap(),
        TechnicalInfo("manufacturer", "model", "1.0", "1.0"),
        "description",
        emptyList(),
        emptyList(),
        emptyList()
    )

    fun createDeviceState() = DeviceState(deviceId, 1, true)
}
