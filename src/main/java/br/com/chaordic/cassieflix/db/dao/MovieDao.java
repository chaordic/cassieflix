package br.com.chaordic.cassieflix.db.dao;

import java.util.Iterator;

import br.com.chaordic.cassieflix.core.pojo.Movie;
import br.com.chaordic.cassieflix.core.pojo.PagedMovies;

import com.google.common.base.Optional;

public interface MovieDao {

    Movie update(Movie movie);

    Optional<Movie> get(String movieId);

    Iterator<Movie> getAll(Optional<Integer> limit);

    Iterator<Movie> getAllByName(Optional<String> startString, Optional<String> endString, Optional<Integer> limit);

    PagedMovies getAllPaged(Long startToken, Integer limit);

}
