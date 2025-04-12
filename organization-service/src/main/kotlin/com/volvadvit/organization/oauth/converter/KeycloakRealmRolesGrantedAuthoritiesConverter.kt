package com.volvadvit.organization.oauth.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.util.Assert


class KeycloakRealmRolesGrantedAuthoritiesConverter: Converter<Jwt, Collection<GrantedAuthority>> {

    private var authorityPrefix = ""

    override fun convert(source: Jwt): Collection<GrantedAuthority>? {
        val roles = ((source.claims["resource_access"] as? Map<*, *>)
            ?.get("ostock") as? Map<*, *>)
            ?.get("roles") as? Collection<*>

        return roles
            ?.filterIsInstance<String>()
            ?.map { SimpleGrantedAuthority(this.authorityPrefix + it) }
            ?.toSet()
            ?: emptySet()
    }

    fun setAuthorityPrefix(authorityPrefix: String): KeycloakRealmRolesGrantedAuthoritiesConverter {
        Assert.notNull(authorityPrefix, "authorityPrefix cannot be null")
        this.authorityPrefix = authorityPrefix
        return this
    }
}