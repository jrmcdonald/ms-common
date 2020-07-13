package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
public final class OAuth2ResourceServerConfigurationProperties {

    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {

        private static final String DEFAULT_JWK_SET_URI = "https://qwyck.eu.auth0.com/.well-known/jwks.json";

        private String jwkSetUri = DEFAULT_JWK_SET_URI;
    }
}
