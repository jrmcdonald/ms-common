package com.jrmcdonald.common.ext.spring.core.openapi.config;

import com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiScope;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

import static com.jrmcdonald.common.ext.spring.core.openapi.model.OpenApiConstants.SCHEME_BEARER;
import static java.util.Collections.emptyList;

@Data
@Validated
@ConfigurationProperties(prefix = "openapi")
public class OpenApiConfigurationProperties {

    private @NotEmpty String title;
    private @NotEmpty String description;
    private @NotEmpty String version;
    private Security security = new Security();
    private @Valid List<Server> servers = emptyList();

    @Data
    public static class Security {

        private static final String EMPTY_STRING = "";

        private String scheme = SCHEME_BEARER;
        private String authorizationUrl = EMPTY_STRING;
        private String refreshUrl = EMPTY_STRING;
        private String tokenUrl = EMPTY_STRING;
        private List<OpenApiScope> scopes = emptyList();

    }

    @Data
    public static class Server {

        private @NotEmpty String url;
        private @NotEmpty String description;
    }
}
