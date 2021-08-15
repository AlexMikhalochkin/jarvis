package com.mega.demo.controller.generated.api

import java.util.Optional
import javax.annotation.Generated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
@Controller
class YandexApiController(@Autowired(required = false) delegate: YandexApiDelegate?) : YandexApi {
    private val delegate: YandexApiDelegate

    init {
        this.delegate = Optional.ofNullable(delegate).orElse(object : YandexApiDelegate {})
    }

    override fun getDelegate(): YandexApiDelegate = delegate
}
