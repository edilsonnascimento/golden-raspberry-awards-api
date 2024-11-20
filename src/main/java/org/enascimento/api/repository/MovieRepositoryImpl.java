package org.enascimento.api.repository;

import org.enascimento.api.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT * FROM movie",
                                  (result, rowNum) ->
             new Movie (result.getLong("id"),
                             result.getString("year_award"),
                             result.getString("title"),
                             result.getString("studios"),
                             result.getString("producers"),
                             result.getString("winner"))
        );
    }

    @Override
    public void saveAll(List<Movie> movies) {
        movies.forEach(this::saveMovie);
    }

    private void saveMovie(Movie movie) {
        jdbcTemplate.update(
                "INSERT INTO movie (year_award, title, studios, producers, winner) VALUES (?, ?, ?, ?, ?)",
                movie.getYear(), movie.getTitle(), movie.getStudios(), movie.getProducers(), movie.getWinner()
        );
    }
}