package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import com.jrmcdonald.ext.spring.core.oauth2.config.OAuth2JwtConfigurationProperties;
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

class OAuth2JwtConfigurationPropertiesTest {

    @Test
    @DisplayName("Should default issuer when not present")
    void shouldDefaultIssuerWhenNotPresent() {
        Binder binder = new Binder(new ArrayList<>());
        OAuth2JwtConfigurationProperties bound = binder.bindOrCreate("jwt.issuer", Bindable.of(OAuth2JwtConfigurationProperties.class));

        assertThat(bound.getJwt().getIssuer()).isEqualTo("https://qwyck.eu.auth0.com/");
    }

    @Test
    @DisplayName("Should default audience when not present")
    void shouldDefaultAudienceWhenNotPresent() {
        Binder binder = new Binder(new ArrayList<>());
        OAuth2JwtConfigurationProperties bound = binder.bindOrCreate("jwt.audience", Bindable.of(OAuth2JwtConfigurationProperties.class));

        assertThat(bound.getJwt().getAudience()).isEqualTo("https://ms.qwyck-cloud.co.uk/");
    }

    @Test
    @DisplayName("Should bind issuer when supplied")
    void shouldBindIssuerWhenSupplied() {

        Map<String, String> properties = new HashMap<>();
        properties.put("jwt.issuer", "issuer");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OAuth2JwtConfigurationProperties> bindResult = binder.bind("", Bindable.of(OAuth2JwtConfigurationProperties.class));

        assertThat(bindResult.get().getJwt().getIssuer()).isEqualTo("issuer");
    }

    @Test
    @DisplayName("Should bind audience when supplied")
    void shouldBindAudienceWhenSupplied() {

        Map<String, String> properties = new HashMap<>();
        properties.put("jwt.audience", "audience");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OAuth2JwtConfigurationProperties> bindResult = binder.bind("", Bindable.of(OAuth2JwtConfigurationProperties.class));

        assertThat(bindResult.get().getJwt().getAudience()).isEqualTo("audience");
    }
}