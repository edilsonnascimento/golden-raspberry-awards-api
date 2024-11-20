package org.enascimento.api.controller;

import org.enascimento.api.dto.ProducesWinnerResponseDto;
import org.enascimento.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/min-max-winners")
    public ProducesWinnerResponseDto findMinAndMaxWinnerRange() {
        return movieService.findMinAndMaxWinnerRange();
    }
}