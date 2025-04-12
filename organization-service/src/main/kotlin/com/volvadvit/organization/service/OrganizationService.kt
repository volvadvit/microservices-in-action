package com.volvadvit.organization.service

import com.volvadvit.organization.model.Organization
import com.volvadvit.organization.repository.OrganizationRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID


@Service
class OrganizationService(private val repository: OrganizationRepository) {

    fun findById(organizationId: String?): Organization? {
        organizationId?.let {
            val opt: Optional<Organization> = repository.findById(organizationId)
            return opt.get()
        } ?: throw IllegalArgumentException("Organization ID must not be empty or null")
    }

    fun create(organization: Organization): Organization {
        organization.id = UUID.randomUUID().toString()
        return repository.save(organization)
    }

    fun update(organization: Organization?) {
        organization?.let {
            repository.save(organization)
        } ?: throw IllegalStateException("Organization must not be null or empty")
    }

    fun delete(organizationId: String) {
        val organization = findById(organizationId)
        organization?.let {
            repository.delete(organization)
        } ?: throw IllegalArgumentException("Organization not found for provided ID")
    }
}