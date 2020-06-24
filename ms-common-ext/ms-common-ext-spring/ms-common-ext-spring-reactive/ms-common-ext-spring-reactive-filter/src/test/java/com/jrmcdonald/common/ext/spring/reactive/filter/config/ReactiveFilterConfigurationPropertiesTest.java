package com.jrmcdonald.common.ext.spring.reactive.filter.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveFilterConfigurationPropertiesTest {

    @Test
    @DisplayName("Should default excluded-paths when not present")
    void shouldDefaultExcludedPathsWhenNotPresent() {
        Binder binder = new Binder(new ArrayList<>());
        ReactiveFilterConfigurationProperties bound = binder.bindOrCreate("excluded-paths", Bindable.of(ReactiveFilterConfigurationProperties.class));

        assertThat(bound.getExcludedPaths()).containsExactly("/actuator", "/actuator/**", "/**/actuator", "/**/actuator/**");
    }

    @Test
    @DisplayName("Should bind excluded-paths when supplied")
    void shouldBindExcludedPathsWhenSupplied() {
        Map<String, String> properties = new HashMap<>();
        properties.put("excluded-paths", "path1,path2");

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        BindResult<ReactiveFilterConfigurationProperties> bindResult = binder.bind("", Bindable.of(ReactiveFilterConfigurationProperties.class));

        assertThat(bindResult.get().getExcludedPaths()).containsExactly("path1", "path2");
    }
}