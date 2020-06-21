package com.jrmcdonald.common.ext.spring.reactive.oauth2.client.credentials.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ClientCredentialsReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(ClientCredentialsConfigurationProperties.class)
public class ReactiveClientCredentialsConfiguration {

    @Bean
    @SuppressWarnings("unchecked")
    public ExchangeFilterFunction tokenRequestFilter(ClientCredentialsConfigurationProperties properties) {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            ClientRequest.Builder builder = ClientRequest.from(request);

            BodyInserters.FormInserter<String> body = (BodyInserters.FormInserter<String>) request.body();
            body.with("audience", properties.getAudience());

            return Mono.just(builder.build());
        });
    }

    @Bean(name = "clientCredentialsWebClient")
    public WebClient clientCredentialsWebClient(ExchangeFilterFunction tokenRequestFilter) {
        return WebClient.builder()
                        .filter(tokenRequestFilter)
                        .build();
    }

    @Bean
    public WebClientReactiveClientCredentialsTokenResponseClient tokenResponseClient(@Qualifier("clientCredentialsWebClient") WebClient clientCredentialsWebClient) {
        WebClientReactiveClientCredentialsTokenResponseClient tokenResponseClient = new WebClientReactiveClientCredentialsTokenResponseClient();
        tokenResponseClient.setWebClient(clientCredentialsWebClient);

        return tokenResponseClient;
    }

    @Bean
    public ReactiveOAuth2AuthorizedClientProvider reactiveOAuth2AuthorizedClientProvider(WebClientReactiveClientCredentialsTokenResponseClient tokenResponseClient) {
        ClientCredentialsReactiveOAuth2AuthorizedClientProvider clientProvider = new ClientCredentialsReactiveOAuth2AuthorizedClientProvider();
        clientProvider.setAccessTokenResponseClient(tokenResponseClient);

        return clientProvider;
    }

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(ReactiveClientRegistrationRepository clientRegistrationRepository,
                                                                         ReactiveOAuth2AuthorizedClientService authorizedClientRepository,
                                                                         ReactiveOAuth2AuthorizedClientProvider reactiveClientProvider) {
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(reactiveClientProvider);
        return authorizedClientManager;
    }

    @Bean
    public ServerOAuth2AuthorizedClientExchangeFilterFunction authorizedClientFilter(ReactiveOAuth2AuthorizedClientManager authorizedClientManager,
                                                                                     ClientCredentialsConfigurationProperties properties) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction authorizedClientFilter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        authorizedClientFilter.setDefaultClientRegistrationId(properties.getDefaultClientRegistrationId());
        return authorizedClientFilter;
    }
}
