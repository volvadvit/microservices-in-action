package com.volvadvit.organization.oauth.converter

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

class KeycloakJwtAuthenticationConverter: JwtAuthenticationConverter() {

    init {
        val grantedAuthoritiesConverter = KeycloakRealmRolesGrantedAuthoritiesConverter()
        grantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX)
        setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
        setPrincipalClaimName(PRINCIPAL_CLAIM_NAME)
    }

    companion object {
        const val PRINCIPAL_CLAIM_NAME: String = "preferred_username"
        const val AUTHORITY_PREFIX: String = "ROLE_"
    }
}