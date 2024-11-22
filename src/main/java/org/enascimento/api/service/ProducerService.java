package org.enascimento.api.service;

import org.enascimento.api.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    @Autowired
    private MovieService movieService;

    public ProducerWinnerResponseDto findMinAndMaxWinnerRange() {
        Set<ProducerWinnerDto> producersWinner = movieService.findProducerWinners();
        Map<String, List<Integer>> producersWinnerMapYears = returnProducerMapper(producersWinner);
        List<ProducerResponseDto> producersResponse = returnProducerResponse(producersWinnerMapYears);
        return returnProducesWinnerResponseDto(producersResponse);
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

    private ProducerWinnerResponseDto returnProducesWinnerResponseDto(List<ProducerResponseDto> producersResponse) {
        List<ProducerResponseDto> min = producersResponse.stream()
                .filter(producerResponseDto -> producerResponseDto.getInterval() == 1)
                .toList();
        List<ProducerResponseDto> max = producersResponse.stream()
                .filter(producerResponseDto -> producerResponseDto.getInterval() != 1)
                .toList();
        return new ProducerWinnerResponseDto(min, max);
    }
}