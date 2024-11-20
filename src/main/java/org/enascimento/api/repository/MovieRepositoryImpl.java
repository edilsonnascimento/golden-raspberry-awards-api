package org.enascimento.api.repository;

import org.enascimento.api.domain.Movie;
import org.enascimento.api.dto.ProducersWinnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProducersWinnerDto> findAllProducerWinner() {
        return jdbcTemplate.query("""
                                       SELECT producers,
                                              year_award
                                       FROM movie
                                       WHERE winner = 'yes'
                                       ORDER BY producers
                                       """,
                                  (result, rowNum) ->
             new ProducersWinnerDto(result.getString("producers"),
                                    Integer.valueOf(result.getString("year_award"))));
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