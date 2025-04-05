package com.volvadvit.license.repository

import com.volvadvit.license.model.entity.License
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LicenseRepository: CrudRepository<License, String> {

    fun findByOrganizationId(organizationId: String): License?

    fun findAllByOrganizationId(organizationId: String): List<License>

    fun findByOrganizationIdAndLicenseId(organizationId: String, licenseId: String): License?
}