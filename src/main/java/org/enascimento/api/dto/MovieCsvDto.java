package org.enascimento.api.dto;


public record MovieCsvDto(
        String year,
        String title,
        String studios,
        String producers,
        String winne) {
}