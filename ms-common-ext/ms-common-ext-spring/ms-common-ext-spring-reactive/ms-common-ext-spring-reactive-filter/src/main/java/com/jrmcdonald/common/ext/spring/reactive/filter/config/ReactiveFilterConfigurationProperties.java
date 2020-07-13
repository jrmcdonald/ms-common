package com.jrmcdonald.common.ext.spring.reactive.filter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "filter.logging")
public class ReactiveFilterConfigurationProperties {

    private List<String> excludedPaths = List.of("/actuator", "/actuator/**", "/**/actuator", "/**/actuator/**");

}
