package org.enascimento.api.repository;

import org.enascimento.api.domain.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();
}