package com.jrmcdonald.common.ext.spring.core.oauth2;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {

    private static final OAuth2Error AUD_ERROR = new OAuth2Error("JWT_INVALID_AUD", "Invalid audience", null);

    private final String audience;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(AUD_ERROR);
        }
    }
}
