package com.am.jarvis.connector.smartthings.controller

import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.ResponseEntity

/**
 * Verification for [SmartThingsApiDelegateImpl].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class SmartThingsApiDelegateImplTest {

    @MockK
    private lateinit var requestHandlers: Map<String, (SmartThingsRequest) -> ResponseEntity<SmartThingsResponse>>

    @InjectMockKs
    private lateinit var delegate: SmartThingsApiDelegateImpl

    @Test
    fun testHandleSmartThings() {
        val smartThingsRequest = mockk<SmartThingsRequest>()
        val handler = mockk<(SmartThingsRequest) -> ResponseEntity<SmartThingsResponse>>()
        val smartThingsResponse = SmartThingsResponse()
        val interactionType = "interactionType"
        val responseEntity = ResponseEntity.ok(smartThingsResponse)
        every { smartThingsRequest.headers.interactionType } returns interactionType
        every { requestHandlers.getValue(interactionType) } returns handler
        every { handler.invoke(smartThingsRequest) } returns responseEntity

        assertSame(responseEntity, delegate.handleSmartThings(smartThingsRequest))
    }
}
