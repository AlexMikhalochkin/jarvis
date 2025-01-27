package com.am.jarvis.notifier

import com.am.jarvis.JarvisApplication
import com.am.jarvis.connector.yandex.notifier.YandexApiClient
import com.am.jarvis.controller.generated.model.NotificationPayload
import com.am.jarvis.controller.generated.model.YandexNotificationRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.bind.annotation.RequestMethod

/**
 * Test for [YandexApiClient].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [JarvisApplication::class])
@ActiveProfiles("test")
class YandexApiClientTest {

    private val objectMapper = jacksonObjectMapper()

    @Autowired
    private lateinit var yandexApiClient: YandexApiClient

    @MockBean(name = "mqttClientListener")
    lateinit var mqttClientListener: IMqttClient

    @MockBean(name = "mqttClientPublisher")
    lateinit var mqttClientPublisher: IMqttClient

    @Value("\${yandex.notification-url}")
    private lateinit var yandexUrl: String

    @Value("\${client.retry.max-attempts}")
    private var maxAttempts: Int? = null

    private val yandexNotificationRequest = YandexNotificationRequest(
        NotificationPayload(
            "userId",
            emptyList()
        ),
        123456L
    )

    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        //todo fix port
        mockWebServer.start(12345)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testNotify() {
        mockWebServer.enqueue(MockResponse())

        yandexApiClient.notify(yandexNotificationRequest)

        val request = mockWebServer.takeRequest()
        assertNotNull(request)
        assertEquals(RequestMethod.POST.toString(), request.method)
        assertEquals(yandexUrl, request.requestUrl.toString())
        assertEquals("OAuth 123456", request.headers["Authorization"])
        val readValue = objectMapper.readValue(request.body.inputStream(), YandexNotificationRequest::class.java)
        assertEquals(yandexNotificationRequest, readValue)
    }

    @Test
    fun testNotifyClientError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(403))

        assertThrows<Exception> { yandexApiClient.notify(yandexNotificationRequest) }
    }

    @Test
    fun testNotifyServerError() {
        generateSequence { MockResponse().setResponseCode(502) }
            .take(maxAttempts!!)
            .forEach { mockWebServer.enqueue(it) }
        mockWebServer.enqueue(MockResponse())

        assertDoesNotThrow { yandexApiClient.notify(yandexNotificationRequest) }
    }

    @Test
    fun testNotifyServerErrorRetryExhausted() {
        generateSequence { MockResponse().setResponseCode(502) }
            .take(maxAttempts!! + 1)
            .forEach { mockWebServer.enqueue(it) }
        mockWebServer.enqueue(MockResponse())

        assertThrows<Exception> { yandexApiClient.notify(yandexNotificationRequest) }
    }
}
