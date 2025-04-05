package com.volvadvit.license.controller

import com.volvadvit.license.model.entity.License
import com.volvadvit.license.service.LicenseService
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Locale

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
class LicenseController(private val licenseService: LicenseService) {

    @GetMapping("/{licenseId}")
    fun getLicense(@PathVariable("licenseId") licenseId: String,
                   @PathVariable("organizationId") organizationId: String,
                   @RequestHeader(value = "Accept-Language",required = false) locale: Locale?): ResponseEntity<License> {

        val license = licenseService.getLicense(licenseId, organizationId, locale)

        return ResponseEntity.ok(
            licenseWithLinks(license, organizationId, locale)
        )
    }

    @PutMapping
    fun updateLicense(@PathVariable("organizationId") organizationId: String,
                      @RequestBody request: License,
                      @RequestHeader(value = "Accept-Language",required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            licenseService.updateLicense(
                request,
                organizationId,
                locale
            )
        )
    }

    @PostMapping
    fun createLicense(@PathVariable("organizationId") organizationId: String,
                      @RequestBody request: License,
                      @RequestHeader(value = "Accept-Language",required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            licenseService.createLicense(
                request,
                organizationId,
                locale
            )
        )
    }

    @DeleteMapping("/{licenseId}")
    fun deleteLicense(@PathVariable("organizationId") organizationId: String,
                      @PathVariable("licenseId") licenseId: String?,
                      @RequestHeader(value = "Accept-Language",required = false) locale: Locale?
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            licenseService.deleteLicense(
                licenseId,
                locale
            )
        )
    }

    @GetMapping()
    fun getAllLicensesForOrganization(@PathVariable("organizationId") organizationId: String): ResponseEntity<List<License>> {
        return ResponseEntity.ok(licenseService.getLicensesByOrganizationId(organizationId))
    }

    /**
     * add HATEOAS links
     */
    private fun licenseWithLinks(license: License, organizationId: String, locale: Locale?): License {
        license.add(
            linkTo(methodOn(LicenseController::class.java)
                .getLicense(license.licenseId!!, organizationId, locale))
                .withSelfRel(),
            linkTo(methodOn(LicenseController::class.java)
                .createLicense(organizationId, license, locale))
                .withRel("createLicense"),
            linkTo(methodOn(LicenseController::class.java)
                .updateLicense(organizationId, license, locale))
                .withRel("updateLicense"),
            linkTo(methodOn(LicenseController::class.java)
                .deleteLicense(organizationId, license.licenseId, locale))
                .withRel("deleteLicense")
        )

        return license
    }
}