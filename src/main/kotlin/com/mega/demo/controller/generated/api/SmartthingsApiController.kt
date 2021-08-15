package com.mega.demo.controller.generated.api

import java.util.Optional
import javax.annotation.Generated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
@Controller
class SmartthingsApiController(@Autowired(required = false) delegate: SmartthingsApiDelegate?) :
    SmartthingsApi {
    private val delegate: SmartthingsApiDelegate

    init {
        this.delegate = Optional.ofNullable(delegate).orElse(object : SmartthingsApiDelegate {})
    }

    override fun getDelegate(): SmartthingsApiDelegate = delegate
}
