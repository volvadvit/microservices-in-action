package com.volvadvit.license.model.entity

import org.springframework.hateoas.RepresentationModel

class Organization(
    var id: String? = null,
    var name: String? = null,
    var contactName: String? = null,
    var contactPhone: String? = null,
    var contactEmail: String? = null
): RepresentationModel<Organization>() {

}