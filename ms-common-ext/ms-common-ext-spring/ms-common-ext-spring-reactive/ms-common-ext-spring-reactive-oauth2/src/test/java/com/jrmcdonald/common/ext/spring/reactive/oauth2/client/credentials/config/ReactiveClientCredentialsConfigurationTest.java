package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app.ReactiveClientCredentialsConfigurationApplication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import lombok.Data;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.test.StepVerifier;

import static com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config.app.ReactiveClientCredentialsConfigurationController.ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(classes = ReactiveClientCredentialsConfigurationApplication.class)
class ReactiveClientCredentialsConfigurationTest {

    @Autowired
    private WebTestClient webTestClient;

    private MockWebServer mockWebServer;

    private MockWebServer mockIdentityProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8081);

        mockIdentityProvider = new MockWebServer();
        mockIdentityProvider.start(8082);
    }

    @AfterEach
    void afterEach() throws IOException {
        mockWebServer.close();
        mockIdentityProvider.close();
    }

    @Data
    private static class TokenResponse {

        private final String token_type = "Bearer";
        private final String access_token = "some-token-value";
        private final long expires_in = 3600;
    }

    @Test
    @DisplayName("Should request client credentials oauth token")
    void shouldRequestClientCredentialsOauthToken() throws Exception {
        MockResponse tokenResponse = new MockResponse();
        tokenResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        tokenResponse.setBody(objectMapper.writeValueAsString(new TokenResponse()));
        mockIdentityProvider.enqueue(tokenResponse);

        MockResponse webResponse = new MockResponse();
        webResponse.setBody("authorized-endpoint-response");
        mockWebServer.enqueue(webResponse);

        FluxExchangeResult<String> result = webTestClient.post().uri(ENDPOINT + "/requests-client-credentials")
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .exchange()
                                                         .expectStatus().isOk()
                                                         .returnResult(String.class);

        StepVerifier.create(result.getResponseBody())
                    .assertNext(resultString -> assertThat(resultString).isEqualTo("authorized-endpoint-response"))
                    .verifyComplete();

        RecordedRequest recordedWebRequest = mockWebServer.takeRequest();
        assertThat(recordedWebRequest.getHeader(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer some-token-value");

        RecordedRequest recordedClientCredentialsRequest = mockIdentityProvider.takeRequest();
        assertThat(recordedClientCredentialsRequest.getBody().readUtf8()).isEqualTo("grant_type=client_credentials&scope=some%3Ascope&audience=test-audience");
    }
}