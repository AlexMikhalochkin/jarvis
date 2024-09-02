package com.am.jarvis

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import org.springframework.web.util.UriComponentsBuilder

private val logger = KotlinLogging.logger {}

/**
 * Stub login controller for development.
 * Must be reimplemented using Spring Security.
 *
 * @author Alex Mikhalochkin
 */
@Controller
class StubLoginController {

    private val code = "711ecb4f-200b-406b-967b-1b1db0953bd8"
    private val token = "ca3e4771-a0e8-4de3-ba63-a3545d15bfb2"

    @RequestMapping(path = ["/smartthings/auth"], method = [RequestMethod.GET])
    fun login(
        @RequestParam(value = "state", required = false) state: String?,
        @RequestParam(value = "response_type", required = false) responseType: String?,
        @RequestParam(value = "redirect_uri", required = false) redirectUri: String?,
        @RequestParam(value = "client_id", required = false) clientId: String?
    ): RedirectView {
        logger.info(
            "Auth post. started. state={}, responseType={}, redirectUri={}, clientId={},",
            state, responseType, redirectUri, clientId
        )
        val redirectView = RedirectView()
        val redirectUriFull = UriComponentsBuilder.fromHttpUrl(redirectUri!!)
            .queryParam("state", state)
            .queryParam("code", code)
            .toUriString()
        redirectView.url = redirectUriFull
        logger.info(
            "Auth post. finished. state={}, responseType={}, redirectUri={}, clientId={}, redirectUriFull={},",
            state, responseType, redirectUri, clientId, redirectUriFull
        )
        return redirectView
    }

    private val i = 1576799999

    @Suppress("Indentation", "LongParameterList")
    @RequestMapping(path = ["/smartthings/token"], method = [RequestMethod.POST])
    fun getToken(
        @RequestParam(value = "client_secret", required = false) clientSecret: String?,
        @RequestParam(value = "client_id", required = false) clientId: String?,
        @RequestParam(value = "grant_type", required = false) grantType: String?,
        @RequestParam(value = "redirect_uri", required = false) redirectUri: String?,
        @RequestParam(value = "refresh_token", required = false) refreshToken: String?,
        @RequestParam(value = "code", required = false) code: String?
    ): ModelAndView {
        logger.info(
            "token. started. clientSecret={}, clientId={}, grantType={}, refreshToken={}, redirectUri={}, code={},",
            clientSecret, clientId, grantType, refreshToken, redirectUri, code
        )
        val modelAndView = ModelAndView(MappingJackson2JsonView())
        modelAndView.addObject("access_token", token)
        modelAndView.addObject("expires_in", i)
        modelAndView.addObject("token_type", "bearer")
        modelAndView.addObject("refresh_token", "5d8rr9d7-a988-0a45-955c-74068fh8ur0l")
        modelAndView.addObject("scope", "x:devices:* r:devices:*")
        logger.info(
            "token. finished. clientSecret={}, clientId={}, grantType={}, refreshToken={}, redirectUri={}, code={},",
            clientSecret, clientId, grantType, refreshToken, redirectUri, code
        )
        return modelAndView
    }
}
