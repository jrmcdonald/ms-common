package com.jrmcdonald.common.ext.spring.web.oauth2.jwt.config;

import com.jrmcdonald.common.ext.spring.core.oauth2.JwtAudienceValidator;
import com.jrmcdonald.common.ext.spring.core.oauth2.config.OAuth2ResourceServerConfigurationProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableConfigurationProperties(OAuth2ResourceServerConfigurationProperties.class)
public class JwtDecoderConfiguration {

    @Bean
    public JwtDecoder jwtDecoder(OAuth2ResourceServerConfigurationProperties properties,
                                 JwtIssuerValidator issuerValidator,
                                 JwtAudienceValidator audienceValidator,
                                 JwtTimestampValidator timestampValidator) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.getJwt().getJwkSetUri())
                .build();

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                timestampValidator,
                issuerValidator,
                audienceValidator));

        return jwtDecoder;
    }
}
