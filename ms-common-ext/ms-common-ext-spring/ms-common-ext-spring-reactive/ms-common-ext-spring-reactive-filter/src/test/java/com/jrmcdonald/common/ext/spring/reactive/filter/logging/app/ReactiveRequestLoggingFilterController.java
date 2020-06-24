package com.jrmcdonald.common.ext.spring.reactive.filter.logging.app;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import static com.jrmcdonald.common.ext.spring.reactive.filter.logging.app.ReactiveRequestLoggingFilterController.ENDPOINT;

@RestController
@RequestMapping(ENDPOINT)
public class ReactiveRequestLoggingFilterController {

    public static final String ENDPOINT = "/v1/logging";

    @PostMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> postTest() {
        return Mono.just(ResponseEntity.ok("hello world"));
    }

    @PostMapping(path = "/excluded", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> postExcluded() {
        return Mono.just(ResponseEntity.ok("goodbye world"));
    }

    @PostMapping(path = "/no-start-time", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> postNoStartTime(ServerWebExchange exchange) {
        exchange.getAttributes().remove("requestStartTime");
        return Mono.just(ResponseEntity.ok("goodbye world"));
    }
}
