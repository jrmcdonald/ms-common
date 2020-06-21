package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "client.credentials")
public class ClientCredentialsConfigurationProperties {

    private static final String DEFAULT_AUDIENCE = "https://ms.qwyck-cloud.co.uk/";
    private static final String DEFAULT_PROVIDER_ID = "auth0";

    private final String audience;
    private final String defaultClientRegistrationId;

    public ClientCredentialsConfigurationProperties(@DefaultValue(DEFAULT_AUDIENCE) String audience,
                                                    @DefaultValue(DEFAULT_PROVIDER_ID) String defaultClientRegistrationId) {
        this.audience = audience;
        this.defaultClientRegistrationId = defaultClientRegistrationId;
    }
}
