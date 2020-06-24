package com.jrmcdonald.common.ext.spring.reactive.context.lifter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

import static com.jrmcdonald.common.ext.spring.reactive.context.model.ReactorContextKeys.CONTEXT_KEYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveContextLifterTest {

    @Mock
    private CoreSubscriber coreSubscriber;

    private ReactiveContextLifter reactiveContextLifter;

    @BeforeEach
    void beforeEach() {
        reactiveContextLifter = new ReactiveContextLifter(coreSubscriber);
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(coreSubscriber);
        MDC.clear();
    }

    @Test
    @DisplayName("Should pass subscription in onSubscribe to CoreSubscriber")
    void shouldPassSubscriptionInOnSubscribeToCoreSubscriber() {
        Subscription expectedSubscription = mock(Subscription.class);
        reactiveContextLifter.onSubscribe(expectedSubscription);
        verify(coreSubscriber).onSubscribe(eq(expectedSubscription));
    }

    @Test
    @DisplayName("Should pass throwable in onError to CoreSubscriber")
    void shouldPassThrowableInOnErrorToCoreSubscriber() {
        Throwable expectedThrowable = new Throwable();
        reactiveContextLifter.onError(expectedThrowable);
        verify(coreSubscriber).onError(eq(expectedThrowable));
    }

    @Test
    @DisplayName("Should call onComplete on CoreSubscriber in onComplete")
    void shouldCallOnCompleteOnCoreSubscriberInOnComplete() {
        reactiveContextLifter.onComplete();
        verify(coreSubscriber).onComplete();
    }

    @Test
    @DisplayName("Should return CoreSubscriber context in currentContext")
    void shouldReturnCoreSubscriberContextInCurrentContext() {
        Context expectedContext = mock(Context.class);
        when(coreSubscriber.currentContext()).thenReturn(expectedContext);

        Context actualContext = reactiveContextLifter.currentContext();

        assertThat(actualContext).isEqualTo(expectedContext);
    }

    @DisplayName("onNext Tests")
    @Nested
    class OnNextTests {

        @Test
        @DisplayName("Should call CoreSubscriber onNext and populate MDC when Reactor Context is set")
        void shouldCallCoreSubscriberOnNextAndPopulateMdcWhenReactorContextIsSet() {
            Object expectedObject = mock(Object.class);

            Map<String, String> contextMap = CONTEXT_KEYS.stream().collect(Collectors.toMap(key -> key, key -> key));
            Context reactorContext = Context.of(contextMap);

            when(coreSubscriber.currentContext()).thenReturn(reactorContext);

            reactiveContextLifter.onNext(expectedObject);

            assertThat(MDC.getCopyOfContextMap()).containsAllEntriesOf(contextMap);

            verify(coreSubscriber).onNext(eq(expectedObject));
        }

        @Test
        @DisplayName("Should call CoreSusbscriber onNext and clean MDC when Reactor Context is not set")
        void shouldCallCoreSusbscriberOnNextAndCleanMdcWhenReactorContextIsNotSet() {
            Object expectedObject = mock(Object.class);

            Map<String, String> contextMap = CONTEXT_KEYS.stream().collect(Collectors.toMap(key -> key, key -> key));
            contextMap.forEach(MDC::put);

            MDC.put("unrelated-key", "unrelated-value");

            when(coreSubscriber.currentContext()).thenReturn(Context.of(Collections.emptyMap()));

            reactiveContextLifter.onNext(expectedObject);

            assertThat(MDC.getCopyOfContextMap().entrySet()).doesNotContainAnyElementsOf(contextMap.entrySet());
            assertThat(MDC.getCopyOfContextMap()).containsEntry("unrelated-key", "unrelated-value");

            verify(coreSubscriber).onNext(eq(expectedObject));
        }
    }
}