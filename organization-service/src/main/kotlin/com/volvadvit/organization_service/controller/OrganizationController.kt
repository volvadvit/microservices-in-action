package com.volvadvit.organization_service.controller

import com.volvadvit.organization_service.model.Organization
import com.volvadvit.organization_service.service.OrganizationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value= ["v1/organization"])
class OrganizationController(private val service: OrganizationService) {

    @GetMapping(value = ["/{organizationId}"])
    fun getOrganization(@PathVariable("organizationId") organizationId: String?): ResponseEntity<Organization> {
        return ResponseEntity.ok(service.findById(organizationId))
    }

    @PostMapping
    fun createOrganization(@RequestBody organization: Organization): ResponseEntity<Organization> {
        return ResponseEntity.ok(service.create(organization))
    }

    @PutMapping("/{organizationId}")
    fun updateOrganization(
        @PathVariable("organizationId") id: String,
        @RequestBody organization: Organization
    ): ResponseEntity<String> {
        service.update(organization)
        return ResponseEntity.ok("Organization was updated")
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrganization(@PathVariable("organizationId") id: String): ResponseEntity<String> {
        service.delete(id)
        return ResponseEntity.ok("Organization was deleted")
    }

}