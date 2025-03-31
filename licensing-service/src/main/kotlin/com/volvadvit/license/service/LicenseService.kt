package com.volvadvit.license.service

import com.volvadvit.license.config.TestServiceProperties
import lombok.extern.slf4j.Slf4j
import com.volvadvit.license.model.License
import com.volvadvit.license.repository.LicenseRepository
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.Locale
import java.util.UUID

@Slf4j
@Service
class LicenseService(
    private val messages: MessageSource,
    private val licenseRepository: LicenseRepository,
    private val config: TestServiceProperties
) {

    fun getLicense(licenseId: String?, organizationId: String?, locale: Locale?): License {
        val license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId!!, licenseId!!) ?:
            throw IllegalArgumentException(
                String.format(
                    messages.getMessage("license.search.error.message", null, locale ?: Locale.getDefault()),
                    licenseId, organizationId
                )
            )

        return license.withComment(config.property!!)
    }

    fun createLicense(license: License?, organizationId: String, locale: Locale?): String {
        license?.let {
            it.licenseId = UUID.randomUUID().toString()
            it.organizationId = organizationId
            licenseRepository.save(it)
        } ?: throw IllegalArgumentException("Licence could not be empty or null")
        return String.format(messages.getMessage("license.create.message", null, locale!!), license.toString())
    }

    fun updateLicense(license: License?, organizationId: String?, locale: Locale?): String {
        license?.let { licenseRepository.save(it) } ?: throw IllegalArgumentException("Licence could not be empty or null")
        return String.format(messages.getMessage("license.update.message", null, locale!!), license.toString())
    }

    fun deleteLicense(licenseId: String?, locale: Locale?): String {
        licenseId?.let {
            licenseRepository.deleteById(licenseId)
        } ?: throw IllegalArgumentException("Licence ID could not be empty or null")
        return String.format(messages.getMessage("license.delete.message", null, locale!!), licenseId)
    }

}