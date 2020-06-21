package com.jrmcdonald.common.ext.spring.core.oauth2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAudienceValidatorTest {

    @Mock
    Jwt jwt;

    private JwtAudienceValidator audienceValidator;

    @BeforeEach
    void beforeEach() {
        audienceValidator = new JwtAudienceValidator("http://audience.example");
    }

    @Test
    @DisplayName("Should validate correct audience")
    void shouldValidateCorrectAudience() {
        when(jwt.getAudience()).thenReturn(Collections.singletonList("http://audience.example"));

        OAuth2TokenValidatorResult result = audienceValidator.validate(jwt);

        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("Should validate incorrect audience")
    void shouldValidateIncorrectAudience() {
        when(jwt.getAudience()).thenReturn(Collections.singletonList("http://bad.example"));

        OAuth2TokenValidatorResult result = audienceValidator.validate(jwt);

        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getErrors()).hasSize(1);

        OAuth2Error error = result.getErrors().iterator().next();
        assertThat(error.getErrorCode()).isEqualTo("JWT_INVALID_AUD");
        assertThat(error.getDescription()).isEqualTo("Invalid audience");
    }
}