package com.mega.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class DemoApplication{

	/**
	 * WebClient for MegaD controller.
	 *
	 * @return instance of [WebClient].
	 */
	@Bean
	fun webClient(): WebClient {
		return  WebClient.create("http://192.168.100.14/sec/")
	}
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
