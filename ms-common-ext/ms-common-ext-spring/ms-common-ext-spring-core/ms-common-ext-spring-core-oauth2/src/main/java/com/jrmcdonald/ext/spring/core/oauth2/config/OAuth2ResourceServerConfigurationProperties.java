package com.jrmcdonald.ext.spring.core.oauth2.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
public final class OAuth2ResourceServerConfigurationProperties {

    private final Jwt jwt;

    public OAuth2ResourceServerConfigurationProperties(@DefaultValue Jwt jwt) {
        this.jwt = jwt;
    }

    @Getter
    public static class Jwt {

        private static final String DEFAULT_JWK_SET_URI = "https://qwyck.eu.auth0.com/.well-known/jwks.json";

        private final String jwkSetUri;

        public Jwt(@DefaultValue(DEFAULT_JWK_SET_URI) String jwkSetUri) {
            this.jwkSetUri = jwkSetUri;
        }
    }
}
