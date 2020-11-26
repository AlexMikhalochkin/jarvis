package com.mikhalochkin.jarvis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mikhalochkin.jarvis.model.Room;
import com.mikhalochkin.jarvis.service.LightService;

/**
 * Controller for lights.
 *
 * @author Alex Mikhalochkin
 */
@Controller
public class LightController {

    @Autowired
    private LightService lightService;

    /**
     * Turn on the light in the room.
     *
     * @param room room identifier.
     * @return empty response.
     */
    @RequestMapping(path = "/rooms/{room}", method = RequestMethod.POST)
    public ResponseEntity<Void> turnOn(@PathVariable String room) {
        lightService.turnOn(Room.valueOf(room));
        return ResponseEntity.ok().build();
    }

    /**
     * Turn off the light in the room.
     *
     * @param room room identifier.
     * @return empty response.
     */
    @RequestMapping(path = "/rooms/{room}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> turnOff(@PathVariable String room) {
        lightService.turnOff(Room.valueOf(room));
        return ResponseEntity.ok().build();
    }
}
