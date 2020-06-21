package com.jrmcdonald.common.ext.spring.reactive.security.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ReactiveSpringSecurityAuthenticationFacadeTest {

    private ReactiveSpringSecurityAuthenticationFacade reactiveSpringSecurityAuthenticationFacade;

    @BeforeEach
    void beforeEach() {
        reactiveSpringSecurityAuthenticationFacade = new ReactiveSpringSecurityAuthenticationFacade();
    }

    @Test
    @WithMockUser
    @DisplayName("Should return Authentication object")
    void shouldReturnAuthenticationObject() {
        StepVerifier.create(reactiveSpringSecurityAuthenticationFacade.getAuthentication())
                    .assertNext(authentication -> assertThat(authentication).isInstanceOf(Authentication.class))
                    .verifyComplete();
    }

    @Test
    @WithMockUser
    @DisplayName("Should return customerId from Authentication")
    void shouldReturnCustomerIdFromAuthentication() {
        StepVerifier.create(reactiveSpringSecurityAuthenticationFacade.getCustomerId())
                    .assertNext(customerId -> assertThat(customerId).isEqualTo("user"))
                    .verifyComplete();
    }
}