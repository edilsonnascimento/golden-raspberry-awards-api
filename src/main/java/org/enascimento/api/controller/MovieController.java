package org.enascimento.api.controller;

import org.enascimento.api.service.MovieService;
import org.enascimento.api.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public List<Movie> findAll() {
        return movieService.findAll();
    }
}