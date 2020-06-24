package com.jrmcdonald.common.ext.spring.reactive.filter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "filter.logging")
public class ReactiveFilterConfigurationProperties {

    private final List<String> excludedPaths;

    public ReactiveFilterConfigurationProperties(@DefaultValue({"/actuator", "/actuator/**", "/**/actuator", "/**/actuator/**"}) List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }
}
