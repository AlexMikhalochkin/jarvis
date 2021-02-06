package com.mikhalochkin.jarvis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final String code = "711ecb4f-200b-406b-967b-1b1db0953bd8";
    private final String token = "ca3e4771-a0e8-4de3-ba63-a3545d15bfb2";

    @RequestMapping(path = "/smartthings/auth", method = RequestMethod.GET)
    public RedirectView login(@RequestParam(value = "scope", required = false) String scope,
                              @RequestParam(value = "response_type", required = false) String responseType,
                              @RequestParam(value = "redirect_uri", required = false) String redirectUri,
                              @RequestParam(value = "client_id", required = false) String clientId) {
        logger.info("Auth post. started. scope={}, responseType={}, redirectUri={}, clientId={},",
                scope, responseType, redirectUri, clientId);
        RedirectView redirectView = new RedirectView();
        String redirectUriFull = UriComponentsBuilder.fromHttpUrl(redirectUri)
                //.queryParam("state", state)
                .queryParam("code", code)
                .toUriString();
        redirectView.setUrl(redirectUriFull);
        logger.info("Auth post. finished. scope={}, responseType={}, redirectUri={}, clientId={}, redirectUriFull={},",
                scope, responseType, redirectUri, clientId, redirectUriFull);
        return redirectView;
    }

    @RequestMapping(path = "/smartthings/token", method = RequestMethod.POST)
    public ModelAndView getToken(@RequestParam(value = "client_secret", required = false) String clientSecret,
                                 @RequestParam(value = "client_id", required = false) String clientId,
                                 @RequestParam(value = "grant_type", required = false) String grantType,
                                 @RequestParam(value = "redirect_uri", required = false) String redirectUri,
                                 @RequestParam(value = "refresh_token", required = false) String refreshToken,
                                 @RequestParam(value = "code", required = false) String code) {
        logger.info("token post. started. clientSecret={}, clientId={}, grantType={}, refreshToken={}, redirectUri={}, code={},",
                clientSecret, clientId, grantType, refreshToken, redirectUri, code);
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("access_token", token);
        modelAndView.addObject("token_type", "Bearer");
        logger.info("token post. finished. clientSecret={}, clientId={}, grantType={}, refreshToken={}, redirectUri={}, code={},",
                clientSecret, clientId, grantType, refreshToken, redirectUri, code);
        return modelAndView;
    }
}
