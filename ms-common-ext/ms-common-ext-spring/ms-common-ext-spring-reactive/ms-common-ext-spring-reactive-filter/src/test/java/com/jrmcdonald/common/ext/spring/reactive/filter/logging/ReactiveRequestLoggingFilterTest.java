package com.jrmcdonald.common.ext.spring.reactive.filter.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

import com.jrmcdonald.common.ext.spring.reactive.context.model.ReactorContextKeys;
import com.jrmcdonald.common.ext.spring.reactive.filter.logging.app.ReactiveRequestLoggingFilterApplication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jrmcdonald.common.ext.spring.reactive.filter.logging.app.ReactiveRequestLoggingFilterController.ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@AutoConfigureWebTestClient
@SpringBootTest(classes = ReactiveRequestLoggingFilterApplication.class)
class ReactiveRequestLoggingFilterTest {

    @SpyBean
    Clock clock;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void beforeEach() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @AfterEach
    void afterEach() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    @DisplayName("Should log service entry message")
    void shouldLogServiceEntryMessage() {
        webTestClient.post().uri(ENDPOINT + "/test")
                     .contentType(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        verify(mockAppender, atLeast(1)).doAppend(captorLoggingEvent.capture());

        List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();

        Map<String, String> expectedMdcProperties = Map.of(
                ReactorContextKeys.APPLICATION, "ms-common",
                ReactorContextKeys.HTTP_METHOD, "POST",
                ReactorContextKeys.URI, "/v1/logging/test"
        );

        Optional<LoggingEvent> serviceEntryEvent = loggingEvents.stream()
                                                                .filter(event -> event.getFormattedMessage().matches("Entering service"))
                                                                .findFirst();

        assertThat(serviceEntryEvent).isPresent();
        assertThat(serviceEntryEvent.get().getMDCPropertyMap()).containsAllEntriesOf(expectedMdcProperties);
    }

    @Test
    @DisplayName("Should log service exit message")
    void shouldLogServiceExitMessage() {
        Instant start = Instant.now();
        Instant end = start.plusSeconds(5);

        when(clock.instant()).thenReturn(start, end);

        webTestClient.post().uri(ENDPOINT + "/test")
                     .contentType(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        verify(mockAppender, atLeast(1)).doAppend(captorLoggingEvent.capture());

        List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();

        Map<String, String> expectedMdcProperties = Map.of(
                ReactorContextKeys.APPLICATION, "ms-common",
                ReactorContextKeys.HTTP_METHOD, "POST",
                ReactorContextKeys.HTTP_STATUS_CODE, "200",
                ReactorContextKeys.URI, "/v1/logging/test"
        );

        Optional<LoggingEvent> serviceEntryEvent = loggingEvents.stream()
                                                                .filter(event -> event.getFormattedMessage().matches("Exiting service"))
                                                                .findFirst();

        assertThat(serviceEntryEvent).isPresent();
        assertThat(serviceEntryEvent.get().getMDCPropertyMap()).containsAllEntriesOf(expectedMdcProperties);
        assertThat(serviceEntryEvent.get().getMDCPropertyMap()).containsKey(ReactorContextKeys.DURATION);
        assertThat(serviceEntryEvent.get().getMDCPropertyMap().get(ReactorContextKeys.DURATION)).isEqualTo(String.valueOf(Duration.between(start, end).toMillis()));
    }

    @Test
    @DisplayName("Should not log duration when start time is null")
    void shouldNotLogDurationWhenStartTimeIsNull() {
        webTestClient.post().uri(ENDPOINT + "/no-start-time")
                     .contentType(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        verify(mockAppender, atLeast(1)).doAppend(captorLoggingEvent.capture());

        List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();

        Optional<LoggingEvent> serviceEntryEvent = loggingEvents.stream()
                                                                .filter(event -> event.getFormattedMessage().matches("Exiting service"))
                                                                .findFirst();

        assertThat(serviceEntryEvent).isPresent();
        assertThat(serviceEntryEvent.get().getMDCPropertyMap()).doesNotContainKey(ReactorContextKeys.DURATION);
    }

    @Test
    @DisplayName("Should not log requests to excluded endpoints")
    void shouldNotLogRequestsToExcludedEndpoints() {
        webTestClient.post().uri(ENDPOINT + "/excluded")
                     .contentType(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus().isOk();

        verifyNoInteractions(mockAppender);
    }

}