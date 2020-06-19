package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import com.jrmcdonald.ext.spring.core.oauth2.config.OAuth2ResourceServerConfigurationProperties;
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

class OAuth2ResourceServerConfigurationPropertiesTest {

    private static final String JWKS_ENDPOINT = "https://qwyck.eu.auth0.com/.well-known/jwks.json";

    @Test
    @DisplayName("Should default jwk-set-uri when not present")
    void shouldDefaultJwkSetUriWhenNotPresent() {
        Binder binder = new Binder(new ArrayList<>());
        OAuth2ResourceServerConfigurationProperties bound = binder.bindOrCreate("jwt.jwk-set-uri", Bindable.of(OAuth2ResourceServerConfigurationProperties.class));

        assertThat(bound.getJwt().getJwkSetUri()).isEqualTo(JWKS_ENDPOINT);
    }

    @Test
    @DisplayName("Should bind jwk-set-uri when specified")
    void shouldBindJwkSetUriWhenSpecified() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jwt.jwk-set-uri", JWKS_ENDPOINT);

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OAuth2ResourceServerConfigurationProperties> bindResult = binder.bind("", Bindable.of(OAuth2ResourceServerConfigurationProperties.class));

        assertThat(bindResult.get().getJwt().getJwkSetUri()).isEqualTo(JWKS_ENDPOINT);
    }
}