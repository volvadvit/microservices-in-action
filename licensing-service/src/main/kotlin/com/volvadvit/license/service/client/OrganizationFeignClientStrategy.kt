package com.volvadvit.license.service.client

import com.volvadvit.license.model.entity.Organization
import com.volvadvit.license.utils.ORGANIZATION_SERVICE_GET_ORGANIZATION_END_POINT
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("organization-service")
interface OrganizationFeignClientStrategy {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = [ORGANIZATION_SERVICE_GET_ORGANIZATION_END_POINT],
        consumes = ["application/json"]
    )
    fun getOrganization(@PathVariable("organizationId") organizationId: String): Organization?
}