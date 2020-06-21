package com.jrmcdonald.common.ext.spring.core.oauth2.config;

import com.jrmcdonald.common.ext.spring.core.oauth2.JwtAudienceValidator;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(OAuth2JwtConfigurationProperties.class)
public class JwtValidatorConfiguration {

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
}
