package com.jrmcdonald.common.test.container.factory;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("rawtypes")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostgresqlContainerFactory {


    private static final String DEFAULT_TAG = "9.6.12";
    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("postgres");

    private static PostgreSQLContainer instance;

    public static PostgreSQLContainer getInstance() {
        if (instance == null) {
            instance = new PostgreSQLContainer<>(DEFAULT_IMAGE_NAME.withTag(DEFAULT_TAG));
        }
        return instance;
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + instance.getJdbcUrl(),
                    "spring.datasource.username=" + instance.getUsername(),
                    "spring.datasource.password=" + instance.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
