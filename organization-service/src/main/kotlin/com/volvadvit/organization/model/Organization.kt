package com.volvadvit.organization.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "organizations")
class Organization(
    @Id
    @Column(name = "organization_id", nullable = false)
    var id: String? = null,

    @Column(nullable = false)
    val name: String? = null,

    @Column(name = "contact_name", nullable = false)
    val contactName: String? = null,

    @Column(name = "contact_email", nullable = false)
    val contactEmail: String? = null,

    @Column(name = "contact_Phone", nullable = false)
    val contactPhone: String? = null
)