package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "client.credentials")
public class ClientCredentialsConfigurationProperties {

    private static final String DEFAULT_AUDIENCE = "https://ms.qwyck-cloud.co.uk/";
    private static final String DEFAULT_PROVIDER_ID = "auth0";

    private String audience = DEFAULT_AUDIENCE;
    private String defaultClientRegistrationId = DEFAULT_PROVIDER_ID;

}
