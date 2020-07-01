package com.jrmcdonald.common.ext.spring.core.openapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.BEARER_FORMAT_JWT;
import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.SCHEME_BEARER;
import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiScope.CUSTOMER_READ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

class OpenApiConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(OpenApiConfiguration.class)
            .withPropertyValues("openapi.title=title_value",
                                "openapi.description=description_value",
                                "openapi.version=version_value",
                                "openapi.servers[0].url=servers_0_url",
                                "openapi.servers[0].description=servers_0_description");

    @Test
    @DisplayName("Should create bearer SecurityScheme bean when bearer is specified")
    void shouldCreateBearerSecuritySchemeBeanWhenBearerIsSpecified() {
        runner.withPropertyValues("openapi.security.scheme=bearer")
              .run(ctx -> {
                  assertThat(ctx.getBean("bearerSecurityScheme")).isInstanceOf(SecurityScheme.class);
                  assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean("clientCredentialsSecurityScheme"));

                  SecurityScheme bean = ctx.getBean("bearerSecurityScheme", SecurityScheme.class);
                  assertThat(bean.getType()).isEqualTo(SecurityScheme.Type.HTTP);
                  assertThat(bean.getDescription()).isEqualTo("Bearer Token Scheme");
                  assertThat(bean.getIn()).isEqualTo(SecurityScheme.In.HEADER);
                  assertThat(bean.getScheme()).isEqualTo(SCHEME_BEARER);
                  assertThat(bean.getBearerFormat()).isEqualTo(BEARER_FORMAT_JWT);
              });
    }

    @Test
    @DisplayName("Should create bearer SecurityScheme bean when no scheme is specified")
    void shouldCreateBearerSecuritySchemeBeanWhenNoSchemeIsSpecified() {
        runner.run(ctx -> {
            assertThat(ctx.getBean("bearerSecurityScheme")).isInstanceOf(SecurityScheme.class);
            assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean("clientCredentialsSecurityScheme"));
        });
    }

    @Test
    @DisplayName("Should create client_credentials SecurityScheme bean when client-credentials is specified")
    void shouldCreateClientCredentialsSecuritySchemeBeanWhenClientCredentialsIsSpecified() {
        runner.withPropertyValues("openapi.security.scheme=client-credentials",
                                  "openapi.security.authorization-url=http://authz.url",
                                  "openapi.security.refresh-url=http://refresh.url",
                                  "openapi.security.token-url=http://token.url",
                                  "openapi.security.scopes=CUSTOMER_READ")
              .run(ctx -> {
                  assertThat(ctx.getBean("clientCredentialsSecurityScheme")).isInstanceOf(SecurityScheme.class);
                  assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean("bearerSecurityScheme"));

                  SecurityScheme bean = ctx.getBean("clientCredentialsSecurityScheme", SecurityScheme.class);
                  assertThat(bean.getType()).isEqualTo(SecurityScheme.Type.OAUTH2);
                  assertThat(bean.getDescription()).isEqualTo("Client Credentials Token Scheme");
                  assertThat(bean.getFlows().getClientCredentials().getAuthorizationUrl()).isEqualTo("http://authz.url");
                  assertThat(bean.getFlows().getClientCredentials().getRefreshUrl()).isEqualTo("http://refresh.url");
                  assertThat(bean.getFlows().getClientCredentials().getTokenUrl()).isEqualTo("http://token.url");

                  Scopes expectedScopes = new Scopes();
                  expectedScopes.addString(CUSTOMER_READ.getValue(), CUSTOMER_READ.getDescription());
                  assertThat(bean.getFlows().getClientCredentials().getScopes()).isEqualTo(expectedScopes);
              });
    }

    @Test
    @DisplayName("Should create OpenApi bean")
    void shouldCreateOpenApiBean() {
        runner.withUserConfiguration(MockBeanConfiguration.class)
              .run(ctx -> {
                  assertThat(ctx.getBean("openApi")).isInstanceOf(OpenAPI.class);

                  OpenAPI bean = ctx.getBean("openApi", OpenAPI.class);
                  assertThat(bean.getSecurity().get(0)).containsKey("bearer");
                  assertThat(bean.getComponents().getSecuritySchemes()).containsEntry("bearer", ctx.getBean("mockSecurityScheme", SecurityScheme.class));
                  assertThat(bean.getInfo().getTitle()).isEqualTo("title_value");
                  assertThat(bean.getInfo().getDescription()).isEqualTo("description_value");
                  assertThat(bean.getInfo().getVersion()).isEqualTo("version_value");
                  assertThat(bean.getServers().get(0).getUrl()).isEqualTo("servers_0_url");
                  assertThat(bean.getServers().get(0).getDescription()).isEqualTo("servers_0_description");
              });
    }

    private static class MockBeanConfiguration {

        @Bean
        @Primary
        public SecurityScheme mockSecurityScheme() {
            return mock(SecurityScheme.class);
        }
    }

}
