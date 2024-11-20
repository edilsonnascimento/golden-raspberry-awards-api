package org.enascimento.api.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private Long id;
    private String year;
    private String title;
    private String studios;
    private String producers;
    private String winner;
}