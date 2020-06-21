package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "security")
public class OAuth2JwtConfigurationProperties {

    private final Jwt jwt;

    public OAuth2JwtConfigurationProperties(@DefaultValue Jwt jwt) {
        this.jwt = jwt;
    }

    @Getter
    public static class Jwt {

        private static final String DEFAULT_AUDIENCE = "https://ms.qwyck-cloud.co.uk/";
        private static final String DEFAULT_ISSUER = "https://qwyck.eu.auth0.com/";

        private final String audience;
        private final String issuer;

        public Jwt(@DefaultValue(DEFAULT_AUDIENCE) String audience, @DefaultValue(DEFAULT_ISSUER) String issuer) {
            this.audience = audience;
            this.issuer = issuer;
        }
    }
}
