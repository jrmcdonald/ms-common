import com.jrmcdonald.common.ext.spring.reactive.security.authentication.ReactiveAuthenticationFacade;
import com.jrmcdonald.common.ext.spring.reactive.security.authentication.config.ReactiveAuthenticationConfiguration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveAuthenticationConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(ReactiveAuthenticationConfiguration.class);

    @Test
    @DisplayName("Should create ReactiveAuthenticationFacade bean")
    void shouldCreateReactiveAuthenticationFacadeBean() {
        runner.run(ctx -> assertThat(ctx.getBean("reactiveAuthenticationFacade")).isInstanceOf(ReactiveAuthenticationFacade.class));
    }
}