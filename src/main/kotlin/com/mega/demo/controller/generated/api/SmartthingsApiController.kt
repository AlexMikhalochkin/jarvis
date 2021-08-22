package com.mega.demo.controller.generated.api

import javax.annotation.Generated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
@Controller
class SmartthingsApiController(@Autowired delegate: SmartthingsApiDelegate) : SmartthingsApi {
    private val delegate: SmartthingsApiDelegate = delegate

    override fun getDelegate(): SmartthingsApiDelegate = delegate
}
