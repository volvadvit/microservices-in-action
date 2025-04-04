package com.volvadvit.license.config

import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@EnableAsync
@ConditionalOnProperty(name = ["scheduler.enabled"], matchIfMissing = true)
class SchedulerConfiguration {

    @Bean
    fun lockProvider(connectionFactory: RedisConnectionFactory): LockProvider {
        return RedisLockProvider(connectionFactory)
    }
}