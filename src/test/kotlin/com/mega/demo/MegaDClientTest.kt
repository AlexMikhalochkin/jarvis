package com.mega.demo

import com.mega.demo.integration.MegaDClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MegaDClientTest {

    var mockBackEnd: MockWebServer = MockWebServer()

    @BeforeAll
    fun setUp() {
        mockBackEnd.start()
    }

    @AfterAll
    fun tearDown() {
        mockBackEnd.shutdown()
    }

    @Test
    fun testTurnOn() {
        val baseUrl = String.format("http://localhost:%s", mockBackEnd.port)
        val webClient = WebClient.create(baseUrl)
        val megaDClient = MegaDClient(webClient)
        mockBackEnd.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()))
        val mono = megaDClient.turnOn(1)
        StepVerifier.create(mono)
            .verifyComplete()
        val takeRequest = mockBackEnd.takeRequest()
        assertEquals("GET", takeRequest.method)
        assertEquals("/?cmd=1:1", takeRequest.path)
    }

    @Test
    fun testTurnOff() {
        val baseUrl = String.format("http://localhost:%s", mockBackEnd.port)
        val webClient = WebClient.create(baseUrl)
        val megaDClient = MegaDClient(webClient)
        mockBackEnd.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()))
        val mono = megaDClient.turnOff(1)
        StepVerifier.create(mono)
            .verifyComplete()
        val takeRequest = mockBackEnd.takeRequest()
        assertEquals("GET", takeRequest.method)
        assertEquals("/?cmd=1:0", takeRequest.path)
    }

    @Test
    fun testGetPortStatuses() {
        val baseUrl = String.format("http://localhost:%s", mockBackEnd.port)
        val webClient = WebClient.create(baseUrl)
        val megaDClient = MegaDClient(webClient)
        mockBackEnd.enqueue(
            MockResponse().setResponseCode(HttpStatus.OK.value())
                .setBody("OFF/10;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;ON;OFF;OFF;OFF;OFF;OFF;OFF;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF;OFF;OFF;OFF;OFF;OFF;OFF;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;ON/1;ON/1;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF/0;OFF")
        )
        val mono = megaDClient.getPortStatuses()
        val map = mapOf(
            7 to "ON",
            8 to "OFF",
            9 to "OFF",
            10 to "OFF",
            11 to "OFF",
            12 to "OFF",
            13 to "OFF",
            22 to "OFF",
            23 to "OFF",
            24 to "OFF",
            25 to "OFF",
            26 to "OFF",
            27 to "OFF",
            28 to "OFF"
        )
        StepVerifier.create(mono)
            .expectNext(map)
            .verifyComplete()
        val takeRequest = mockBackEnd.takeRequest()
        assertEquals("GET", takeRequest.method)
        assertEquals("/?cmd=all", takeRequest.path)
    }
}
