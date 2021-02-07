package com.mikhalochkin.jarvis.controller;

import com.mikhalochkin.jarvis.model.*;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;
import com.mikhalochkin.jarvis.service.google.GoogleService;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GoogleController {
    private static final Logger logger = LoggerFactory.getLogger(GoogleController.class);

    @Autowired
    @Qualifier("googleService")
    private SmartHomeService googleService;

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
