package com.jrmcdonald.common.ext.spring.web.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();

    String getCustomerId();
}
