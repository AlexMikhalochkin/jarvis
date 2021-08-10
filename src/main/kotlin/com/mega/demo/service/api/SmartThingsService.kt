package com.mega.demo.service.api

import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.SmartThingsDevice

/**
 * SmartThings service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartThingsService : SmartHomeService<SmartThingsDevice, DeviceState>
