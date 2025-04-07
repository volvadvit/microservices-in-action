package com.volvadvit.license.filter

import org.springframework.stereotype.Component

@Component
class UserContext(
    var correlationId: String? = null,
    var authToken: String? = null,
    var userId: String? = null,
    var organizationId: String? = null
)