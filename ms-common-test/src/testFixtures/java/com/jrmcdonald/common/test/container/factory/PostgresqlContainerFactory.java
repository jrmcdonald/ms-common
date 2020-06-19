package com.jrmcdonald.common.test.container.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostgresqlContainerFactory {

    private static PostgreSQLContainer instance;

    public static PostgreSQLContainer getInstance() {
        if (instance == null) {
            instance = new PostgreSQLContainer<>();
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
