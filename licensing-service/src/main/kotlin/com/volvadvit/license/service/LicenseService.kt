package com.volvadvit.license.service

import com.volvadvit.license.config.TestServiceConfigurationProperties
import com.volvadvit.license.model.entity.License
import com.volvadvit.license.model.entity.Organization
import com.volvadvit.license.repository.LicenseRepository
import com.volvadvit.license.client.OrganizationFeignClientStrategy
import com.volvadvit.license.filter.UserContextHolder
import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.github.resilience4j.retry.annotation.Retry
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.Locale
import java.util.UUID

@Service
@RefreshScope
class LicenseService(
    private val messages: MessageSource,
    private val licenseRepository: LicenseRepository,
    private val config: TestServiceConfigurationProperties,
    private val organizationClient: OrganizationFeignClientStrategy,
    @Value("\${test.circuitbreaker.timeout.exception.enabled}") val circuitBreakerToggle: Boolean
) {
    private val logger = LoggerFactory.getLogger(LicenseService::class.java)

    fun getLicense(licenseId: String?, organizationId: String?, locale: Locale?): License {
        val license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId!!, licenseId!!) ?:
        throw IllegalArgumentException(
            String.format(
                messages.getMessage("license.search.error.message", null, locale ?: Locale.getDefault()),
                organizationId, licenseId
            )
        )

        val organization = retrieveOrganizationInfo(organizationId)
        organization?.let {
            license.organizationName = organization.name
            license.contactName = organization.contactName
            license.contactEmail = organization.contactEmail
            license.contactPhone = organization.contactPhone
        }

        return license.withComment(config.property)
    }

    @CircuitBreaker(name = "licensingService", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryLicenseService", fallbackMethod="buildFallbackLicenseList")
    @Bulkhead(name= "bulkheadLicenseService", type = Bulkhead.Type.THREADPOOL, fallbackMethod= "buildFallbackLicenseList")
    fun getLicensesByOrganizationId(organizationId: String): List<License> {
        logger.info("getLicensesByOrganization Correlation id: ${UserContextHolder.getContext().correlationId}")
        throwTimeoutExceptionIfNeeded()
        return licenseRepository.findAllByOrganizationId(organizationId)
    }

    fun createLicense(license: License?, organizationId: String, locale: Locale?): String {
        license?.let {
            it.licenseId = UUID.randomUUID().toString()
            it.organizationId = organizationId
            licenseRepository.save(it)
        } ?: throw IllegalArgumentException("Licence could not be empty or null")

        return String.format(
            messages.getMessage("license.create.message", null, locale ?: Locale.getDefault()),
            license.licenseId)
    }

    fun updateLicense(license: License?, organizationId: String?, locale: Locale?): String {
        license?.let {
            licenseRepository.save(it)
        } ?: throw IllegalArgumentException("Licence could not be empty or null")

        return String.format(
            messages.getMessage("license.update.message", null, locale ?: Locale.getDefault()),
            license.toString()
        )
    }

    fun deleteLicense(licenseId: String?, locale: Locale?): String {
        licenseId?.let {
            licenseRepository.deleteById(licenseId)
        } ?: throw IllegalArgumentException("Licence ID could not be empty or null")

        return String.format(messages.getMessage("license.delete.message", null, locale!!), licenseId)
    }

    @CircuitBreaker(name = "organizationService")
    private fun retrieveOrganizationInfo(organizationId: String?): Organization? {
        if (organizationId != null) {
            val oauthTokenFromRequest = UserContextHolder.getContext().authToken
            val correlationId = UserContextHolder.getContext().correlationId
            return organizationClient.getOrganization(oauthTokenFromRequest, correlationId, organizationId)
        } else {
            throw IllegalArgumentException("Wrong input arguments for organizationId or clientType")
        }
    }

    /**
     * Simulates database query latency to test Circuit Breaker
     */
    private fun throwTimeoutExceptionIfNeeded() {
        if (circuitBreakerToggle) sleepAndThrowTimeout(5000L)
    }

    private fun sleepAndThrowTimeout(sleepFor: Long) {
        try {
//            Thread.sleep(sleepFor)
            throw java.util.concurrent.TimeoutException("Timeout exception")
        } catch (ex: InterruptedException) {
            System.err.println(ex.message)
        }
    }

    private fun buildFallbackLicenseList(organizationId: String, throwable: Throwable): List<License> {
        logger.warn("Catching failed request to datasource: ${throwable.message}")

        val licenseList = ArrayList<License>()
        val sampleLicense = License(
            licenseId = "0000-00000-0000-0000",
            organizationId = organizationId,
            productName = "Sorry no licensing information currently available")

        licenseList.add(sampleLicense)
        return licenseList
    }
}