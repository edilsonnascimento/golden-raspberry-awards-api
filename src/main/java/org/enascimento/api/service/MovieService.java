package org.enascimento.api.service;

import org.enascimento.api.dto.*;
import org.enascimento.api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public ProducesWinnerResponseDto findMinAndMaxWinnerRange() {
        Set<ProducerWinnerDto> producersWinner = returnProducerWinners();
        Map<String, List<Integer>> producerMapYears = returnProducerMapper(producersWinner);
        List<ProducerResponseDto> producersResponse = returnProducerResponse(producerMapYears);
        return returnResponse(producersResponse);
    }

    private Set<ProducerWinnerDto> returnProducerWinners() {
        var producerWinners = movieRepository.findAllProducerWinner().stream()
                .flatMap(producer -> Arrays.stream(producer.names().split(", | and"))
                        .map(name -> new ProducerWinnerDto(name.stripLeading(), producer.year()))
                )
                .sorted(Comparator.comparing(ProducerWinnerDto::getName))
                .collect(toList());
        return new LinkedHashSet<>(producerWinners);
    }

    private Map<String, List<Integer>> returnProducerMapper(Set<ProducerWinnerDto> producersWinner) {
        return producersWinner.stream()
                .collect(Collectors.groupingBy(
                        ProducerWinnerDto::getName,
                        Collectors.mapping(ProducerWinnerDto::getYear, Collectors.toList())
                ));
    }

    private List<ProducerResponseDto> returnProducerResponse(Map<String, List<Integer>> producerMapYears) {
        return producerMapYears
                .entrySet()
                .stream()
                .map(entry -> new ProducerResponseDto(
                        entry.getKey(),
                        Collections.min(entry.getValue()),
                        Collections.max(entry.getValue()),
                        Collections.max(entry.getValue()) - Collections.min(entry.getValue())
                ))
                .filter(isMoreThanOneVictory())
                .toList();
    }

    private static Predicate<ProducerResponseDto> isMoreThanOneVictory() {
        return producerResponseDto -> producerResponseDto.getInterval() > 0;
    }

    private ProducesWinnerResponseDto returnResponse(List<ProducerResponseDto> producersResponse) {
        List<ProducerResponseDto> min = producersResponse.stream()
                .filter(producerResponseDto -> producerResponseDto.getInterval() == 1)
                .toList();
        List<ProducerResponseDto> max = producersResponse.stream()
                .filter(producerResponseDto -> producerResponseDto.getInterval() != 1)
                .toList();
        return new ProducesWinnerResponseDto(min, max);
    }
}