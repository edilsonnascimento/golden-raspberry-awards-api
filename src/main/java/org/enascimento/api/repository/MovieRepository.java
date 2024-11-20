package org.enascimento.api.repository;

import org.enascimento.api.domain.Movie;
import org.enascimento.api.dto.ProducersWinnerDto;

import java.util.List;

public interface MovieRepository {
    List<ProducersWinnerDto> findAllProducerWinner();
    void saveAll(List<Movie> movies);
}