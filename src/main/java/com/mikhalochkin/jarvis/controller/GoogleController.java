package com.mikhalochkin.jarvis.controller;

import com.mikhalochkin.jarvis.model.*;
import com.mikhalochkin.jarvis.service.google.GoogleService;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GoogleController {
    private static final Logger logger = LoggerFactory.getLogger(GoogleController.class);

    @Autowired
    private GoogleService googleService;

    @PostMapping("/")
    public GoogleResponse fullfill(@RequestBody GoogleRequest googleRequest) {
        logger.info("fulfill={}", googleRequest);
        GoogleResponse googleResponse = new GoogleResponse();
        googleResponse.setRequestId(googleRequest.getRequestId());
        Input input = googleRequest.getInputs().get(0);
        String intent = input.getIntent();
        if (intent.contains("SYNC")) {
            googleResponse.setPayload(createSyncResponse());
        } else if (intent.contains("QUERY")) {
            googleResponse.setPayload(createQueryResponse(input.getPayload()));
        } else if (intent.contains("EXECUTE")) {
            googleResponse.setPayload(createExecuteResponse(input.getPayload()));
        }
        logger.info("response={}", googleResponse);
        return googleResponse;
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
                .map(GoogleDevice::getId)
                .collect(Collectors.toList());
        return Collections.singletonMap("devices", googleService.getStatuses(deviceIds));
    }

    private Payload createSyncResponse() {
        Payload payload = new Payload();
        //TODO: investigate user id
        payload.setAgentUserId("1836.15267389");
        List<GoogleDevice> allDevices = googleService.getAllDevices().stream()
                .map(this::convertToGoogleDevice)
                .collect(Collectors.toList());
        payload.setDevices(allDevices);
        return payload;
    }

    private GoogleDevice convertToGoogleDevice(Device device) {
        GoogleDevice googleDevice = new GoogleDevice();
        googleDevice.setId(device.getId());
        googleDevice.setName(device.getName());
        googleDevice.setTraits(device.getTraits());
        googleDevice.setType(device.getType());
        googleDevice.setDeviceInfo(device.getDeviceInfo());
        googleDevice.setOtherDeviceIds(device.getOtherDeviceIds());
        googleDevice.setAttributes(device.getAttributes());
        googleDevice.setCustomData(device.getCustomData());
        googleDevice.setRoomHint(device.getRoomHint());
        googleDevice.setWillReportState(device.isWillReportState());
        return googleDevice;
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
