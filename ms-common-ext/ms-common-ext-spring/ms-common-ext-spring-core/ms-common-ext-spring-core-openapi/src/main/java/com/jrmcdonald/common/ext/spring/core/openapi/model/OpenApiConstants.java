package com.jrmcdonald.common.ext.spring.core.openapi.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenApiConstants {

    public static final String SCHEME_BEARER = "bearer";
    public static final String SCHEME_CLIENT_CREDENTIALS = "client-credentials";
    public static final String BEARER_FORMAT_JWT = "jwt";
}
