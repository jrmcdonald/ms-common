package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ClientCredentialsConfigurationPropertiesTest {

    private static final String DEFAULT_AUDIENCE = "https://ms.qwyck-cloud.co.uk/";
    private static final String DEFAULT_PROVIDER_ID = "auth0";

    @Test
    @DisplayName("Should default audience when not present")
    void shouldDefaultAudienceWhenNotPresent() {
        Binder binder = new Binder(new ArrayList<>());
        ClientCredentialsConfigurationProperties bound = binder.bindOrCreate("audience", Bindable.of(ClientCredentialsConfigurationProperties.class));

        assertThat(bound.getAudience()).isEqualTo(DEFAULT_AUDIENCE);
    }

    @Test
    @DisplayName("Should bind audience when present")
    void shouldBindAudienceWhenPresent() {
        Map<String, String> properties = new HashMap<>();
        properties.put("audience", DEFAULT_AUDIENCE);

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<ClientCredentialsConfigurationProperties> bindResult = binder.bind("", Bindable.of(ClientCredentialsConfigurationProperties.class));

        assertThat(bindResult.get().getAudience()).isEqualTo(DEFAULT_AUDIENCE);
    }

    @Test
    @DisplayName("Should default providerId when not present")
    void shouldDefaultProviderIdWhenNotPresent() {
        Binder binder = new Binder(new ArrayList<>());
        ClientCredentialsConfigurationProperties bound = binder.bindOrCreate("default-client-registration-id", Bindable.of(ClientCredentialsConfigurationProperties.class));

        assertThat(bound.getDefaultClientRegistrationId()).isEqualTo(DEFAULT_PROVIDER_ID);
    }

    @Test
    @DisplayName("Should bind providerId when present")
    void shouldBindProviderIdWhenPresent() {
        Map<String, String> properties = new HashMap<>();
        properties.put("default-client-registration-id", DEFAULT_PROVIDER_ID);

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<ClientCredentialsConfigurationProperties> bindResult = binder.bind("", Bindable.of(ClientCredentialsConfigurationProperties.class));

        assertThat(bindResult.get().getDefaultClientRegistrationId()).isEqualTo(DEFAULT_PROVIDER_ID);
    }
}