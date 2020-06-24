package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import static com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app.ReactiveClientCredentialsConfigurationController.ENDPOINT;

@RestController
@RequestMapping(ENDPOINT)
public class ReactiveClientCredentialsConfigurationController {

    public static final String ENDPOINT = "/v1/client-credentials";

    private final WebClient webClient;

    public ReactiveClientCredentialsConfigurationController(ServerOAuth2AuthorizedClientExchangeFilterFunction authorizedClientFilter) {
        webClient = WebClient.builder().filter(authorizedClientFilter).baseUrl("http://localhost:8081").build();
    }

    @PostMapping(path = "/requests-client-credentials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> postTest() {
        return webClient.get().uri("/mock-authorized-endpoint")
                        .retrieve()
                        .bodyToMono(String.class)
                        .map(ResponseEntity::ok);
    }
}
