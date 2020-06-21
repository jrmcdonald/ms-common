package com.jrmcdonald.common.schema.definition.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceExceptionTest {

    @Test
    @DisplayName("Should create ServiceException")
    void shouldCreateNotFoundException() {
        ServiceException exception = new ServiceException();
        assertThat(exception).isInstanceOf(ServiceException.class);
    }
}