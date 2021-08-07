package com.mega.demo

import com.mega.demo.integration.impl.MegaDClient
import java.util.Locale
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MegaDClientTest {

    private lateinit var megaDClient: MegaDClient
    private lateinit var mockWebServer: MockWebServer

    @BeforeAll
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @BeforeEach
    internal fun init() {
        val baseUrl = String.format(Locale.getDefault(), "http://localhost:%s", mockWebServer.port)
        val webClient = WebClient.create(baseUrl)
        megaDClient = MegaDClient(webClient)
    }

    @Test
    fun testTurnOn() {
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()))
        megaDClient.turnOn(1)
        val takeRequest = mockWebServer.takeRequest()
        assertEquals("GET", takeRequest.method)
        assertEquals("/?cmd=1:1", takeRequest.path)
    }

    @Test
    fun testTurnOff() {
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()))
        megaDClient.turnOff(1)
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/?cmd=1:0", request.path)
    }

    @Test
    fun testGetPortStatuses() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(HttpStatus.OK.value())
                .setBody(
                    "OFF/10;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;ON;OFF;OFF;OFF;OFF;OFF;OFF;OFF/0;OFF/0;OFF/0;" +
                            "OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF;OFF;OFF;OFF;OFF;OFF;OFF;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;" +
                            "OFF/0;OFF/0;ON/1;ON/1;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF"
                )
        )
        val mono = megaDClient.getPortStatuses()
        val map = mapOf(
            7 to true,
            8 to false,
            9 to false,
            10 to false,
            11 to false,
            12 to false,
            13 to false,
            22 to false,
            23 to false,
            24 to false,
            25 to false,
            26 to false,
            27 to false,
            28 to false
        )
        assertEquals(map, mono)
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/?cmd=all", request.path)
    }
}
