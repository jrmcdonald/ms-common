package com.jrmcdonald.common.ext.spring.reactive.context.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReactorContextKeys {

    public static final String APPLICATION = "application";
    public static final String DURATION = "duration";
    public static final String HTTP_METHOD = "httpMethod";
    public static final String HTTP_STATUS_CODE = "httpStatus";
    public static final String URI = "uri";

    public static final List<String> CONTEXT_KEYS = List.of(APPLICATION, DURATION, HTTP_METHOD, HTTP_STATUS_CODE, URI);

}
