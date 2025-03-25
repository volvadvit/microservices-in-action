package com.volvadvit.license.service

import lombok.extern.slf4j.Slf4j
import com.volvadvit.license.model.License
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.Locale
import kotlin.random.Random

@Slf4j
@Service
class LicenseService(private val messages: MessageSource) {

    fun getLicense(licenseId: String?, organizationId: String?): License {
        return License(
            Random.nextInt(1000),
            licenseId,
            "Software Product",
            organizationId,
            "Ostock",
            "full"
        );
    }

    fun createLicense(license: License?, organizationId: String, locale: Locale?): String {
        license?.let {it.organizationId = organizationId} ?: throw RuntimeException("Licence could not be empty or null")
        return String.format(messages.getMessage("license.create.message", null, locale!!), license.toString())
    }

    fun updateLicense(license: License?, organizationId: String?, locale: Locale?): String {
        license?.let {it.organizationId = organizationId} ?: throw RuntimeException("Licence could not be empty or null")
        return String.format(messages.getMessage("license.update.message", null, locale!!), license.toString())
    }

    fun deleteLicense(licenseId: String?, organizationId: String?, locale: Locale?): String {
        return String.format(messages.getMessage("license.delete.message", null, locale!!), licenseId, organizationId)
    }

}