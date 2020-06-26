package com.jrmcdonald.common.ext.spring.core.openapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;

import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiScope.CUSTOMER_READ;
import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiScope.CUSTOMER_WRITE;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigurationPropertiesTest {

    private Validator validator;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Should bind title when set")
    void shouldBindTitleWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("title", "title_value");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getTitle()).isEqualTo("title_value");

    }

    @Test
    @DisplayName("Should generate violation constraint when title not set")
    void shouldGenerateConstraintViolationWhenTitleNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("title", Bindable.of(OpenApiConfigurationProperties.class));

        Set<ConstraintViolation<OpenApiConfigurationProperties>> violations = validator.validate(bound);
        assertThat(violations).extracting(ConstraintViolation::getPropertyPath).extracting(Path::toString).contains("title");
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be empty");
    }

    @Test
    @DisplayName("Should bind description when set")
    void shouldBindDescriptionWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("description", "description_value");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getDescription()).isEqualTo("description_value");
    }

    @Test
    @DisplayName("Should generate violation constraint when description not set")
    void shouldGenerateConstraintViolationWhenDescriptionNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("description", Bindable.of(OpenApiConfigurationProperties.class));

        Set<ConstraintViolation<OpenApiConfigurationProperties>> violations = validator.validate(bound);
        assertThat(violations).extracting(ConstraintViolation::getPropertyPath).extracting(Path::toString).contains("description");
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be empty");
    }

    @Test
    @DisplayName("Should bind version when set")
    void shouldBindVersionWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("version", "version_value");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getVersion()).isEqualTo("version_value");
    }

    @Test
    @DisplayName("Should generate violation constraint when version not set")
    void shouldGenerateConstraintViolationWhenVersionNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("version", Bindable.of(OpenApiConfigurationProperties.class));

        Set<ConstraintViolation<OpenApiConfigurationProperties>> violations = validator.validate(bound);
        assertThat(violations).extracting(ConstraintViolation::getPropertyPath).extracting(Path::toString).contains("version");
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be empty");
    }

    @Test
    @DisplayName("Should bind security.scheme when set")
    void shouldBindSecuritySchemeWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("security.scheme", "client_credentials");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getSecurity().getScheme()).isEqualTo("client_credentials");
    }

    @Test
    @DisplayName("Should default security.scheme to bearer when not set")
    void shouldDefaultSecuritySchemeToBearerWhenNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("security.scheme", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bound.getSecurity().getScheme()).isEqualTo("bearer");
    }

    @Test
    @DisplayName("Should bind security.authorization-url when set")
    void shouldBindSecurityAuthorizationUrlWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("security.authorization-url", "http://authz.url");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getSecurity().getAuthorizationUrl()).isEqualTo("http://authz.url");
    }

    @Test
    @DisplayName("Should default security.authorization-url when not set")
    void shouldDefaultSecurityAuthorizationUrlWhenNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("security.authorization-url", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bound.getSecurity().getAuthorizationUrl()).isEmpty();
    }

    @Test
    @DisplayName("Should bind security.refresh-url when set")
    void shouldBindSecurityRefreshUrlWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("security.refresh-url", "http://refresh.url");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getSecurity().getRefreshUrl()).isEqualTo("http://refresh.url");
    }

    @Test
    @DisplayName("Should default security.refresh-url when not set")
    void shouldDefaultSecurityRefreshUrlWhenNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("security.refresh-url", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bound.getSecurity().getAuthorizationUrl()).isEmpty();
    }

    @Test
    @DisplayName("Should bind security.token-url when set")
    void shouldBindSecurityTokenUrlWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("security.token-url", "http://token.url");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getSecurity().getTokenUrl()).isEqualTo("http://token.url");

    }

    @Test
    @DisplayName("Should default security.token-url when not set")
    void shouldDefaultSecurityTokenUrlWhenNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("security.token-url", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bound.getSecurity().getAuthorizationUrl()).isEmpty();
    }

    @Test
    @DisplayName("Should bind security.scopes when set")
    void shouldBindSecurityScopesWhenSet() {
        Map<String, String> properties = new HashMap<>();
        properties.put("security.scopes", "CUSTOMER_READ,CUSTOMER_WRITE");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<OpenApiConfigurationProperties> bindResult = binder.bind("", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bindResult.get().getSecurity().getScopes()).containsExactlyInAnyOrder(CUSTOMER_READ, CUSTOMER_WRITE);
    }

    @Test
    @DisplayName("Should default security.scopes when not set")
    void shouldDefaultSecurityScopesWhenNotSet() {
        Binder binder = new Binder(new ArrayList<>());
        OpenApiConfigurationProperties bound = binder.bindOrCreate("security.scopes", Bindable.of(OpenApiConfigurationProperties.class));

        assertThat(bound.getSecurity().getScopes()).isEqualTo(emptyList());
    }
}