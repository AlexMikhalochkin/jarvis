package com.am.jarvis

import com.am.jarvis.core.model.RetryableServerException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.core.codec.Encoder
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.ViewResolverRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.WebClient
import org.thymeleaf.spring6.ISpringWebFluxTemplateEngine
import org.thymeleaf.spring6.SpringWebFluxTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring6.view.reactive.ThymeleafReactiveViewResolver
import org.thymeleaf.templatemode.TemplateMode
import reactor.util.retry.Retry
import reactor.util.retry.RetryBackoffSpec
import java.time.Duration

@SpringBootApplication
@EnableWebFlux
class JarvisApplication : WebFluxConfigurer, ApplicationContextAware {

    private lateinit var ctx: ApplicationContext

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
    }

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.viewResolver(thymeleafChunkedAndDataDrivenViewResolver())
    }

    override fun setApplicationContext(context: ApplicationContext) {
        this.ctx = context
    }

    @Bean
    fun thymeleafTemplateResolver(): SpringResourceTemplateResolver {
        val resolver = SpringResourceTemplateResolver()
        resolver.setApplicationContext(this.ctx)
        resolver.prefix = "classpath:/templates/"
        resolver.suffix = ".html"
        resolver.templateMode = TemplateMode.HTML
        resolver.isCacheable = false
        resolver.checkExistence = false
        return resolver
    }

    @Bean
    fun thymeleafTemplateEngine(): ISpringWebFluxTemplateEngine {
        val templateEngine = SpringWebFluxTemplateEngine()
        templateEngine.setTemplateResolver(thymeleafTemplateResolver())
        return templateEngine
    }

    @Bean
    @Suppress("MagicNumber")
    fun thymeleafChunkedAndDataDrivenViewResolver(): ThymeleafReactiveViewResolver {
        val viewResolver = ThymeleafReactiveViewResolver()
        viewResolver.templateEngine = thymeleafTemplateEngine()
        viewResolver.responseMaxChunkSizeBytes = 8192
        return viewResolver
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }

    @Bean
    fun encoder(objectMapper: ObjectMapper): Encoder<Any> {
        return Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)
    }

    @Bean
    fun retryBackoffSpec(
        @Value("\${client.retry.max-attempts}") maxAttempts: Long,
        @Value("\${client.retry.delay-millis}") delayMillis: Long
    ): RetryBackoffSpec {
        return Retry.backoff(maxAttempts, Duration.ofMillis(delayMillis))
            .filter { it is RetryableServerException }
    }
}

fun main(args: Array<String>) {
    runApplication<JarvisApplication>(*args)
}
