package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "security")
public class OAuth2JwtConfigurationProperties {

    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {

        private static final String DEFAULT_AUDIENCE = "https://ms.qwyck-cloud.co.uk/";
        private static final String DEFAULT_ISSUER = "https://qwyck.eu.auth0.com/";

        private String audience = DEFAULT_AUDIENCE;
        private String issuer = DEFAULT_ISSUER;

    }
}
