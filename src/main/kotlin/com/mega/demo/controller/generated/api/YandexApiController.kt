package com.mega.demo.controller.generated.api

import javax.annotation.Generated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
@Controller
class YandexApiController(@Autowired delegate: YandexApiDelegate) : YandexApi {
    private val delegate: YandexApiDelegate = delegate

    override fun getDelegate(): YandexApiDelegate = delegate
}
