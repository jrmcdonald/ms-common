package com.jrmcdonald.common.ext.spring.reactive.filter.logging;

import com.jrmcdonald.common.ext.spring.reactive.context.model.ReactorContextKeys;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static java.lang.System.nanoTime;

@Slf4j
@RequiredArgsConstructor
public class ReactiveRequestLoggingFilter implements WebFilter {

    private static final BigDecimal ONE_MILLION = BigDecimal.valueOf(1E6);
    private static final String REQUEST_START_TIME = "requestStartTime";

    private final String applicationName;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Map<String, String> reactorContext = buildReactorContext(exchange.getRequest());

        exchange.getAttributes().put(REQUEST_START_TIME, nanoTime());

        return chain.filter(exchange)
                    .doFirst(() -> {
                        reactorContext.forEach(MDC::put);
                        log.info("Entering service");
                        reactorContext.forEach((k, v) -> MDC.remove(k));
                    })
                    .doFinally(signal -> {
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
        long requestEndTime = nanoTime();
        return BigDecimal.valueOf(requestEndTime)
                         .subtract(BigDecimal.valueOf(requestStartTime))
                         .divide(ONE_MILLION, 0, RoundingMode.UP)
                         .toString();
    }
}
