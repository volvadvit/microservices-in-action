package com.volvadvit.organization.repository

import com.volvadvit.organization.model.Organization
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRepository: CrudRepository<Organization, String> {
}