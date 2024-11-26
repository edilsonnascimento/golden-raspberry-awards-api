package org.enascimento.api.controller;

import org.enascimento.api.service.DateTimeService;
import org.enascimento.api.util.TestIntegrationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class ProducerControllerTest extends TestIntegrationUtil {

    private static final String URI_BASE = "/v1/producers";

    @MockBean
    private DateTimeService dateTimeService;

    @Test
    void shouldReturnMinMaxWinnersData() {

        var expected =
                """
                {
                    "min": [
                        {
                            "name": "Joel Silver",
                            "previousWin": 1990,
                            "followingWin": 1991,
                            "interval": 1
                        }
                    ],
                    "max": [
                        {
                            "name": "Matthew Vaughn",
                            "previousWin": 2002,
                            "followingWin": 2015,
                            "interval": 13
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