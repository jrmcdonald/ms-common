package com.jrmcdonald.common.ext.spring.core.openapi.config;

import com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiScope;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.SCHEME_BEARER;
import static java.util.Collections.emptyList;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "openapi")
public class OpenApiConfigurationProperties {

    private final @NotEmpty String title;
    private final @NotEmpty String description;
    private final @NotEmpty String version;
    private final Security security;
    private final @Valid List<Server> servers;

    public OpenApiConfigurationProperties(String title, String description, String version, @DefaultValue Security security, List<Server> servers) {
        this.title = title;
        this.description = description;
        this.version = version;
        this.security = security;
        this.servers = ((servers == null) || servers.isEmpty()) ? emptyList() : servers;
    }

    @Getter
    public static class Security {

        private static final String EMPTY_STRING = "";

        private final String scheme;
        private final String authorizationUrl;
        private final String refreshUrl;
        private final String tokenUrl;
        private final List<OpenApiScope> scopes;

        public Security(@DefaultValue(SCHEME_BEARER) String scheme,
                        @DefaultValue(EMPTY_STRING) String authorizationUrl,
                        @DefaultValue(EMPTY_STRING) String refreshUrl,
                        @DefaultValue(EMPTY_STRING) String tokenUrl,
                        List<OpenApiScope> scopes) {
            this.scheme = scheme;
            this.authorizationUrl = authorizationUrl;
            this.refreshUrl = refreshUrl;
            this.tokenUrl = tokenUrl;
            this.scopes = (scopes == null) ? emptyList() : scopes;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Server {

        private final @NotEmpty String url;
        private final @NotEmpty String description;
    }
}
