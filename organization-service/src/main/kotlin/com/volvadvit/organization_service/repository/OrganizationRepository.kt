package com.volvadvit.organization_service.repository

import com.volvadvit.organization_service.model.Organization
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRepository: CrudRepository<Organization, String> {
}