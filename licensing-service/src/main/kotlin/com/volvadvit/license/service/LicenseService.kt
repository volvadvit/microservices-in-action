package com.volvadvit.license.service

import com.volvadvit.license.config.TestServiceConfigurationProperties
import com.volvadvit.license.model.entity.License
import com.volvadvit.license.model.entity.Organization
import com.volvadvit.license.repository.LicenseRepository
import com.volvadvit.license.service.client.OrganizationFeignClientStrategy
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
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
    private val organizationClient: OrganizationFeignClientStrategy
) {
    @Value("\${test.circuitbreaker.timeout.exception.enabled}")
    var circuitBreakerToggle: Boolean = false

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

    @CircuitBreaker(name = "licensingService")
    fun getLicensesByOrganizationId(organizationId: String): List<License> {
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
            return organizationClient.getOrganization(organizationId)
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
}