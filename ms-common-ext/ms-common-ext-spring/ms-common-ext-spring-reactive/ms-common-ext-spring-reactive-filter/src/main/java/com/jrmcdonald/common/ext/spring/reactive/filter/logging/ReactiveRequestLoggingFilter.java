package com.jrmcdonald.common.ext.spring.reactive.filter.logging;

import com.jrmcdonald.common.ext.spring.reactive.context.model.ReactorContextKeys;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
@RequiredArgsConstructor
public class ReactiveRequestLoggingFilter implements WebFilter {

    private static final String REQUEST_START_TIME = "requestStartTime";

    private final String applicationName;
    private final List<String> excludedPathPatterns;
    private final Clock clock;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        RequestPath path = exchange.getRequest().getPath();
        AntPathMatcher matcher = new AntPathMatcher();

        if (excludedPathPatterns.stream().anyMatch(pattern -> matcher.match(pattern, path.toString()))) {
            return chain.filter(exchange);
        }

        Map<String, String> reactorContext = buildReactorContext(exchange.getRequest());

        exchange.getAttributes().put(REQUEST_START_TIME, clock.instant().toEpochMilli());

        return chain.filter(exchange)
                    .doFirst(() -> {
                        reactorContext.forEach(MDC::put);
                        log.info("Entering service");
                        reactorContext.forEach((k, v) -> MDC.remove(k));
                    })
                    .doFinally(signal -> {
                        reactorContext.forEach(MDC::put);
                        addDurationAndStatusToMDC(exchange);
                        log.info("Exiting service");
                    })
                    .subscriberContext(ctx -> ctx.putAll(Context.of(reactorContext)));
    }

    private Map<String, String> buildReactorContext(ServerHttpRequest request) {
        Map<String, String> initialContext = new HashMap<>();

        initialContext.put(ReactorContextKeys.APPLICATION, applicationName);
        initialContext.put(ReactorContextKeys.HTTP_METHOD, request.getMethodValue());
        initialContext.put(ReactorContextKeys.URI, request.getURI().toString());

        return initialContext;
    }

    private void addDurationAndStatusToMDC(ServerWebExchange exchange) {
        String timeInNanos = calculateExecutionTime(exchange);
        if (timeInNanos != null) {
            MDC.put(ReactorContextKeys.DURATION, timeInNanos);
        }
        HttpStatus statusCode = exchange.getResponse().getStatusCode();
        if (statusCode != null) {
            MDC.put(ReactorContextKeys.HTTP_STATUS_CODE, String.valueOf(statusCode.value()));
        }
    }

    private String calculateExecutionTime(ServerWebExchange request) {
        Long requestStartTime = request.getAttribute(REQUEST_START_TIME);
        if (requestStartTime == null) {
            return null;
        }
        request.getAttributes().remove(REQUEST_START_TIME);
        return String.valueOf(Duration.between(Instant.ofEpochMilli(requestStartTime), clock.instant()).toMillis());
    }
}
