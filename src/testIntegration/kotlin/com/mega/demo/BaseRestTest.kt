package com.mega.demo

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockWebServer
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
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
import org.springframework.web.reactive.function.client.WebClient

/**
 * Base test for REST endpoints.
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [DemoApplication::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:test.properties"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BaseRestTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var testService: TestService

    @MockBean
    lateinit var mqttClient: IMqttClient

    lateinit var mockWebServer: MockWebServer

    private val mapper = ObjectMapper()

    @BeforeAll
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(12345)
    }

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    fun convertToJson(objectToConvert: Any): String = mapper.writeValueAsString(objectToConvert)

    fun getPayloadFromFile(path: String) = testService.getPayloadFromFile(path)

    fun performRequest(requestBuilder: MockHttpServletRequestBuilder) =
        mvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk)
}
