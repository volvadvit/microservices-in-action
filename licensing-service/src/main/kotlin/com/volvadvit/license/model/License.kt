package com.volvadvit.license.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name="licenses")
class License(
    @Id
    @Column(name = "license_id", nullable = false)
    // @NaturalId
    var licenseId: String? = null,

    val description: String? = null,

    @Column(name = "license_type", nullable = false)
    val licenseType: String? = null,

    @Column(name = "organization_id", nullable = false)
    var organizationId: String? = null,

    @Column(name = "product_name", nullable = false)
    val productName: String? = null,

    @Column(name="comment")
    var comment: String? = null
) : RepresentationModel<License>() {

    fun withComment(comment: String): License {
        this.comment = comment
        return this;
    }
}