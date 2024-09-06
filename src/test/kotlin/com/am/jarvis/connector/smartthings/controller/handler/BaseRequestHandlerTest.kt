package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.Authentication
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.generateUuid
import com.am.jarvis.service.api.SmartHomeService
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity

@ExtendWith(MockKExtension::class)
abstract class BaseRequestHandlerTest<T : (SmartThingsRequest) -> ResponseEntity<SmartThingsResponse>> {

    private val requestId = generateUuid()

    val deviceId = requestId

    @MockK
    lateinit var conversionService: ConversionService

    @MockK
    lateinit var smartHomeService: SmartHomeService

    @InjectMockKs
    lateinit var handler: T

    fun verifyHeaders(body: SmartThingsResponse?, expectedInteractionType: String) {
        val headers = body!!.headers
        assertEquals(requestId, headers!!.requestId)
        assertEquals(expectedInteractionType, headers.interactionType)
    }

    fun executeRequest(
        interactionType: String,
        smartThingsDevices: List<SmartThingsDevice>? = null
    ): ResponseEntity<SmartThingsResponse> {
        val smartThingsRequest = SmartThingsRequest(
            Headers(interactionType = interactionType, requestId = requestId),
            Authentication(),
            smartThingsDevices
        )
        return handler.invoke(smartThingsRequest)
    }

    fun createDeviceState() = DeviceState(deviceId, true, mapOf("port" to 1))
}
