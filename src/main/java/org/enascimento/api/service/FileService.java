package org.enascimento.api.service;

import org.enascimento.api.domain.Movie;
import org.enascimento.api.repository.MovieRepository;
import org.enascimento.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileService {

   @Autowired
    private MovieRepository movieRepository;

    public void importCsvFromResources(String path) {
        FileReader<Movie> fileReader = new CsvReader<>(Movie.class);
        Optional<List<Movie>> movieList = Optional.ofNullable(fileReader.read(path));
        movieList.ifPresent(movies -> movieRepository.saveAll(movies));
    }
}