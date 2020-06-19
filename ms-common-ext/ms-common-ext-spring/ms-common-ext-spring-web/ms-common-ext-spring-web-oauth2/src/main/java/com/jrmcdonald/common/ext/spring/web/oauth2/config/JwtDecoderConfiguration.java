package com.jrmcdonald.common.ext.spring.web.oauth2.config;

import com.jrmcdonald.ext.spring.core.oauth2.JwtAudienceValidator;
import com.jrmcdonald.ext.spring.core.oauth2.config.OAuth2JwtConfigurationProperties;
import com.jrmcdonald.ext.spring.core.oauth2.config.OAuth2ResourceServerConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties({OAuth2ResourceServerConfigurationProperties.class, OAuth2JwtConfigurationProperties.class})
public class JwtDecoderConfiguration {

    @Bean
    public JwtTimestampValidator timestampValidator(Clock clock) {
        JwtTimestampValidator timestampValidator = new JwtTimestampValidator();
        timestampValidator.setClock(clock);
        return timestampValidator;
    }

    @Bean
    public JwtAudienceValidator audienceValidator(OAuth2JwtConfigurationProperties securityProperties) {
        return new JwtAudienceValidator(securityProperties.getJwt().getAudience());
    }

    @Bean
    public JwtIssuerValidator issuerValidator(OAuth2JwtConfigurationProperties securityProperties) {
        return new JwtIssuerValidator(securityProperties.getJwt().getIssuer());
    }

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
