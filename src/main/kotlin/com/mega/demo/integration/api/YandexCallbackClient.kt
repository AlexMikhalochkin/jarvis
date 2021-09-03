package com.mega.demo.integration.api

/**
 * Client to perform request to Yandex.
 *
 * @author Alex Mikhalochkin
 */
interface YandexCallbackClient {

    /**
     * Sends request to Yandex.
     */
    fun send(deviceId: String, isOn: Boolean)
}
