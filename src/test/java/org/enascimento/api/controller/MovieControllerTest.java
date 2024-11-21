package org.enascimento.api.controller;

import org.enascimento.api.service.DateTimeService;
import org.enascimento.api.util.TestIntegrationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

class MovieControllerTest extends TestIntegrationUtil {

    private static final String URI_BASE = "/v1/movies";

    @MockBean
    private DateTimeService dateTimeService;

    @Test
    @Sql(value = "/data/produces-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/data/trunc.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnMinMaxWinnersData() {

        var expected =
                """
                {
                    "min": [
                        {
                            "name": "Diana Blue",
                            "previousWin": 2021,
                            "followingWin": 2022,
                            "interval": 1
                        }
                    ],
                    "max": [
                        {
                            "name": "Olivia Blue",
                            "previousWin": 2015,
                            "followingWin": 2025,
                            "interval": 10
                        }
                    ]
                }
                """;
        webTestClient
                .get().uri(URI_BASE + "/min-max-winners")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .json(expected);
    }
}