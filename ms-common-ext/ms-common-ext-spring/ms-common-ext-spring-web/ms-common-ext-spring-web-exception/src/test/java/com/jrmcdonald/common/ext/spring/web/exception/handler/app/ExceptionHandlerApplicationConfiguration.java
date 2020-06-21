package com.jrmcdonald.common.ext.spring.web.exception.handler.app;

import com.jrmcdonald.common.ext.spring.web.exception.handler.config.ExceptionHandlerConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import(ExceptionHandlerConfiguration.class)
public class ExceptionHandlerApplicationConfiguration {

}
