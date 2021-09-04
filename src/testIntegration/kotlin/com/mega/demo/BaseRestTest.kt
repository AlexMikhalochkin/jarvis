package com.mega.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

/**
 * Base test for REST endpoints.
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [DemoApplication::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application.properties"])
internal class BaseRestTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var testService: TestService

    @MockBean
    lateinit var mqttClient: IMqttClient

    private val mapper = ObjectMapper()

    fun convertToJson(objectToConvert: Any): String = mapper.writeValueAsString(objectToConvert)

    fun getPayloadFromFile(path: String) = testService.getPayloadFromFile(path)

    fun performRequest(requestBuilder: MockHttpServletRequestBuilder) =
        mvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk)
}
