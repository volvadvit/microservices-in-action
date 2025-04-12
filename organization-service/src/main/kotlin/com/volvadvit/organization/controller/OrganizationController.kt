package com.volvadvit.organization.controller

import com.volvadvit.organization.model.Organization
import com.volvadvit.organization.service.OrganizationService
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value= ["v1/organization"])
class OrganizationController(private val service: OrganizationService) {

    @RolesAllowed("ADMIN", "USER")
    @GetMapping(value = ["/{organizationId}"])
    fun getOrganization(@PathVariable("organizationId") organizationId: String?): ResponseEntity<Organization> {
        return ResponseEntity.ok(service.findById(organizationId))
    }

    @RolesAllowed("ADMIN", "USER")
    @PostMapping
    fun createOrganization(@RequestBody organization: Organization): ResponseEntity<Organization> {
        return ResponseEntity.ok(service.create(organization))
    }

    @RolesAllowed("ADMIN", "USER")
    @PutMapping("/{organizationId}")
    fun updateOrganization(
        @PathVariable("organizationId") id: String,
        @RequestBody organization: Organization
    ): ResponseEntity<String> {
        service.update(organization)
        return ResponseEntity.ok("Organization was updated")
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrganization(@PathVariable("organizationId") id: String): ResponseEntity<String> {
        service.delete(id)
        return ResponseEntity.ok("Organization was deleted")
    }

}