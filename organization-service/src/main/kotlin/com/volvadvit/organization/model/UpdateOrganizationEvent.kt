package com.volvadvit.organization.model

class UpdateOrganizationEvent(
    val eventId: String,
    val fieldName: String,
    val newValue: String
) {

}