package com.volvadvit.license.model

import lombok.ToString
import org.springframework.hateoas.RepresentationModel

@ToString
class License(
    val id: Int = 0,
    val licenseId: String? = null,
    val description: String? = null,
    val licenseType: String? = null,
    var organizationId: String? = null,
    val productName: String? = null
) : RepresentationModel<License>() {

}