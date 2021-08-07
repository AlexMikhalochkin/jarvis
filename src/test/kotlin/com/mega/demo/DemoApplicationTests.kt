package com.mega.demo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

class DemoApplicationTests {

	@Test
	fun testWebClient() {
		val demoApplication = DemoApplication()
		ReflectionTestUtils.setField(demoApplication, "plcUrl", "http://test.test/test")
		assertNotNull(demoApplication.webClient())
	}
}
