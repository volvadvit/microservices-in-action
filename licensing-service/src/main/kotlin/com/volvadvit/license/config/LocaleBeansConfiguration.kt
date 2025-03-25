package com.volvadvit.license.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

/**
 * Localization
 */
@Configuration
class LocaleBeansConfiguration {

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = SessionLocaleResolver()
        localeResolver.setDefaultLocale(Locale.US)
        return localeResolver
    }

    @Bean
    fun messageSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setUseCodeAsDefaultMessage(true)
        messageSource.setBasenames("messages")
        return messageSource
    }
}