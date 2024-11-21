package org.enascimento.api.controller;

import org.enascimento.api.repository.MovieRepository;
import org.enascimento.api.service.DateTimeService;
import org.enascimento.api.util.TestIntegrationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

class MovieControllerExceptionTest extends TestIntegrationUtil {

    private static final String URI_BASE = "/v1/movies";

    @MockBean
    private DateTimeService dateTimeService;
    @MockBean
    private MovieRepository movieRepository;

    @Test
    void shouldThrowBadRequestExceptionWhenUriIsInvalid() {

        var dateTimeNow = LocalDateTime.of(2024, 11, 21, 06, 07, 45, 287028276);
        when(dateTimeService.nowLocalDateTime()).thenReturn(dateTimeNow);

        var expected =
                """
                {
                    "type": "about:blank",
                    "title": "Bad Request",
                    "status": 400,
                    "detail": "URI_INVALID",
                    "instance": "/v1/movies/min-max-winners-INVALID",
                    "timestamp": "%s"
                }
                """.formatted(dateTimeNow);
        webTestClient
                .get().uri(URI_BASE + "/min-max-winners-INVALID")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expected);
    }

    @Test
    @Sql(value = "/data/trunc.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowBusinessExceptionWhenNotImportCSV() {

        var dateTimeNow = LocalDateTime.of(2024, 11, 21, 06, 07, 45, 287028276);
        when(dateTimeService.nowLocalDateTime()).thenReturn(dateTimeNow);
        when(movieRepository.findAllProducerWinner()).thenReturn(Optional.empty());

        var expected =
                """
                {
                    "type": "about:blank",
                    "title": "Conflict",
                    "status": 409,
                    "detail": "ERROR_NOT_FOUND_WINNERS",
                    "instance": "/v1/movies/min-max-winners",
                    "timestamp": "%s"
                }
                """.formatted(dateTimeNow);
        webTestClient
                .get().uri(URI_BASE + "/min-max-winners")
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .json(expected);
    }
}