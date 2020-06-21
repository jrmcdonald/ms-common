package com.jrmcdonald.common.ext.spring.reactive.oauth2.jwt.config;

import com.jrmcdonald.common.ext.spring.core.oauth2.JwtAudienceValidator;
import com.jrmcdonald.common.ext.spring.core.oauth2.config.OAuth2ResourceServerConfigurationProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
@EnableConfigurationProperties(OAuth2ResourceServerConfigurationProperties.class)
public class ReactiveJwtDecoderConfiguration {

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder(OAuth2ResourceServerConfigurationProperties properties,
                                                 JwtIssuerValidator issuerValidator,
                                                 JwtAudienceValidator audienceValidator,
                                                 JwtTimestampValidator timestampValidator) {
        NimbusReactiveJwtDecoder jwtDecoder = new NimbusReactiveJwtDecoder(properties.getJwt().getJwkSetUri());

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                timestampValidator,
                issuerValidator,
                audienceValidator));

        return jwtDecoder;
    }
}
