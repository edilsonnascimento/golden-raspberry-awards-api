package org.enascimento.api.service;

import org.enascimento.api.domain.Movie;
import org.enascimento.api.dto.ProducerWinnerDto;
import org.enascimento.api.exception.BusinessException;
import org.enascimento.api.repository.MovieRepository;
import org.enascimento.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void importCsvFromResources(String path) {
        FileReader<Movie> fileReader = new CsvReader<>(Movie.class);
        Optional<List<Movie>> movieList = Optional.ofNullable(fileReader.read(path));
        movieRepository.saveAll(
                movieList.orElseThrow(() -> new BusinessException("ERROR_IMPORT_CSV"))
        );
    }

    public Set<ProducerWinnerDto> findProducerWinners() {
        var producerWinners = movieRepository.findAllProducerWinner()
                .orElseThrow(() -> new BusinessException("ERROR_NOT_FOUND_WINNERS"))
                .stream()
                .flatMap(producer -> Arrays.stream(producer.names().split(", | and"))
                        .map(name -> new ProducerWinnerDto(name.stripLeading(), producer.year()))
                )
                .sorted(Comparator.comparing(ProducerWinnerDto::getName))
                .toList();
        return new LinkedHashSet<>(producerWinners);
    }
}