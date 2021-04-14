package com.jrmcdonald.common.test.container.factory;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.PostgreSQLContainer;

import static com.jrmcdonald.common.test.container.factory.PostgresqlContainerFactory.Initializer;
import static com.jrmcdonald.common.test.container.factory.PostgresqlContainerFactory.getInstance;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("rawtypes")
class PostgresqlContainerFactoryTest {

    @Test
    @DisplayName("Should create instance of a PostgresqlContainer")
    void shouldCreateInstanceOfAPostgresqlContainer() {
        Container container = getInstance();
        assertThat(container).isInstanceOf(PostgreSQLContainer.class);
    }

    @Test
    @DisplayName("Should initialize test properties")
    void shouldInitializeTestProperties() {
        ApplicationContextRunner runner = new ApplicationContextRunner();

        runner.run(ctx -> {
            PostgreSQLContainer container = getInstance();
            container.start();

            Initializer initializer = new Initializer();
            initializer.initialize(ctx);

            assertThat(ctx.getEnvironment().getProperty("spring.datasource.url")).isEqualTo(container.getJdbcUrl());
            assertThat(ctx.getEnvironment().getProperty("spring.datasource.username")).isEqualTo(container.getUsername());
            assertThat(ctx.getEnvironment().getProperty("spring.datasource.password")).isEqualTo(container.getPassword());

            container.stop();
        });
    }
}