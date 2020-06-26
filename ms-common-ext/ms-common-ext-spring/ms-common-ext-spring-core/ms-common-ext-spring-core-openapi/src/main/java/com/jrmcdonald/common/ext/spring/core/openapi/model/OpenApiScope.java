package com.jrmcdonald.common.ext.spring.core.openapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OpenApiScope {

    CUSTOMER_READ("customer:read", "The ability to read from a customer's account data."),
    CUSTOMER_WRITE("customer:write", "The ability to write to a customer's account data.");

    private final String value;
    private final String description;
}
