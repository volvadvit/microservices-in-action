package com.volvadvit.license.repository

import com.volvadvit.license.model.License
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LicenseRepository: CrudRepository<License, String> {

    fun findByOrganizationId(organizationId: String): License?

    fun findByOrganizationIdAndLicenseId(organizationId: String, licenseId: String): License?
}