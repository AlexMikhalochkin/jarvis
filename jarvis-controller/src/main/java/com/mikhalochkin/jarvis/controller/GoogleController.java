package com.mikhalochkin.jarvis.controller;

import com.mikhalochkin.jarvis.model.*;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UriComponentsBuilder;
import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.service.google.GoogleService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GoogleController {
    private static final Logger logger = LoggerFactory.getLogger(GoogleController.class);
    private final String code = "711ecb4f-200b-406b-967b-1b1db0953bd8";
    private final String token = "ca3e4771-a0e8-4de3-ba63-a3545d15bfb2";

    @Autowired
    private GoogleService googleService;

    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public RedirectView login(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "state", required = false) String state,
                              @RequestParam(value = "response_type", required = false) String responseType,
                              @RequestParam(value = "redirect_uri", required = false) String redirectUri,
                              @RequestParam(value = "client_id", required = false) String clientId) {
        logger.info("Auth post. started. username={}, password={}, state={}, responseType={}, redirectUri={}, clientId={},",
                username, password, state, responseType, redirectUri, clientId);
        RedirectView redirectView = new RedirectView();
        String redirectUriFull = UriComponentsBuilder.fromHttpUrl(redirectUri)
                .queryParam("state", state)
                .queryParam("code", code)
                .toUriString();
        redirectView.setUrl(redirectUriFull);
        logger.info("Auth post. finished. username={}, password={}, state={}, responseType={}, redirectUri={}, clientId={}, redirectUriFull={},",
                username, password, state, responseType, redirectUri, clientId, redirectUriFull);
        return redirectView;
    }

    @RequestMapping(path = "/token", method = RequestMethod.POST)
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

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ModelAndView fullfill(@RequestBody GoogleRequest googleRequest) {
        logger.info("fulfill={}", googleRequest);
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("requestId", googleRequest.getRequestId());
        Input input = googleRequest.getInputs().get(0);
        String intent = input.getIntent();
        if (intent.contains("SYNC")) {
            modelAndView.addObject("payload", createSyncResponse());
        } else if (intent.contains("QUERY")) {
            modelAndView.addObject("payload", createQueryResponse(input.getPayload()));
        } else if (intent.contains("EXECUTE")) {
            modelAndView.addObject("payload", createExecuteResponse(input.getPayload()));
        }
        logger.info("response={}", modelAndView);
        return modelAndView;
    }

    private Map<String, List<Map<String, Object>>> createExecuteResponse(Payload payload) {
        Map<Boolean, List<String>> collect = payload.getCommands().stream()
                .map(this::createPair)
                .collect(Collectors.toMap(Pair::getRight, Pair::getLeft, ListUtils::union));
        collect.forEach((key, value) -> googleService.changeStatus(value, key));
        List<Map<String, Object>> commands = collect.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> command = new HashMap<>();
                    command.put("ids", entry.getValue());
                    command.put("status", "SUCCESS");
                    Map<String, Object> states = new HashMap<>();
                    states.put("on", entry.getKey());
                    states.put("online", true);
                    command.put("states", states);
                    return command;
                })
                .collect(Collectors.toList());
        return Collections.singletonMap("commands", commands);
    }

    private Map<String, Object> createQueryResponse(Payload payload) {
        List<String> deviceIds = payload.getDevices().stream()
                .map(Device::getId)
                .collect(Collectors.toList());
        return Collections.singletonMap("devices", googleService.getStatuses(deviceIds));
    }

    private Payload createSyncResponse() {
        Payload payload = new Payload();
        //TODO: investigate user id
        payload.setAgentUserId("1836.15267389");
        payload.setDevices(googleService.getAllDevices());
        return payload;
    }

    private Pair<List<String>, Boolean> createPair(Command command) {
        List<String> ids = command.getDevices().stream().
                map(Device::getId)
                .collect(Collectors.toList());
        Boolean on = command.getExecution().stream()
                .map(Execution::getParams)
                .map(map -> map.get("on"))
                .map(Object::toString)
                .map(Boolean::parseBoolean)
                .findFirst()
                .get();
        return Pair.of(ids, on);
    }
}
