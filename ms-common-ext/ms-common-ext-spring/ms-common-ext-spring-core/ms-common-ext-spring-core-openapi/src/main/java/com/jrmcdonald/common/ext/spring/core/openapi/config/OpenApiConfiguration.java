package com.jrmcdonald.common.ext.spring.core.openapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.BEARER_FORMAT_JWT;
import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.SCHEME_BEARER;
import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.SCHEME_CLIENT_CREDENTIALS;
import static java.util.Collections.singletonList;

@Configuration
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
public class OpenApiConfiguration {

    private static final String SECURITY_PREFIX = "openapi.security";
    private static final String SCHEME_PROPERTY = "scheme";

    @Bean
    @ConditionalOnProperty(prefix = SECURITY_PREFIX, name = SCHEME_PROPERTY, havingValue = SCHEME_BEARER, matchIfMissing = true)
    public SecurityScheme bearerSecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                   .description("Bearer Token Scheme")
                                   .in(SecurityScheme.In.HEADER)
                                   .scheme(SCHEME_BEARER)
                                   .bearerFormat(BEARER_FORMAT_JWT);
    }

    @Bean
    @ConditionalOnProperty(prefix = SECURITY_PREFIX, name = SCHEME_PROPERTY, havingValue = SCHEME_CLIENT_CREDENTIALS)
    public SecurityScheme clientCredentialsSecurityScheme(OpenApiConfigurationProperties properties) {
        Scopes scopes = properties.getSecurity()
                                  .getScopes()
                                  .stream()
                                  .collect(Scopes::new,
                                           (accumulator, scope) -> accumulator.put(scope.getValue(), scope.getDescription()),
                                           Scopes::putAll);

        OAuthFlow clientCredentialsFlow = new OAuthFlow().authorizationUrl(properties.getSecurity().getAuthorizationUrl())
                                                         .refreshUrl(properties.getSecurity().getRefreshUrl())
                                                         .tokenUrl(properties.getSecurity().getTokenUrl())
                                                         .scopes(scopes);

        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                                   .description("Client Credentials Token Scheme")
                                   .flows(new OAuthFlows().clientCredentials(clientCredentialsFlow));
    }

    @Bean
    public OpenAPI openApi(OpenApiConfigurationProperties properties, SecurityScheme securityScheme) {
        return new OpenAPI().components(new Components().addSecuritySchemes(properties.getSecurity().getScheme(), securityScheme))
                            .security(singletonList(new SecurityRequirement().addList(properties.getSecurity().getScheme())))
                            .info(new Info()
                                          .title(properties.getTitle())
                                          .description(properties.getDescription())
                                          .version(properties.getVersion()));
    }
}
