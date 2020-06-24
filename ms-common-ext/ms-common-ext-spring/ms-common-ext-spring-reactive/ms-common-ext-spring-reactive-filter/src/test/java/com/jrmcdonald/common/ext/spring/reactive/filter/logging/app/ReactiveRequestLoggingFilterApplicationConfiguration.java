package com.jrmcdonald.common.ext.spring.reactive.filter.logging.app;

import com.jrmcdonald.common.ext.spring.datetime.config.DateTimeConfiguration;
import com.jrmcdonald.common.ext.spring.reactive.filter.config.ReactiveFilterConfiguration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import({DateTimeConfiguration.class, ReactiveFilterConfiguration.class})
public class ReactiveRequestLoggingFilterApplicationConfiguration {

}
