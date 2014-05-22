package br.com.chaordic.cassieflix.core.dao;

import java.util.Iterator;

import br.com.chaordic.cassieflix.core.pojo.Movie;

import com.google.common.base.Optional;

public interface MovieDao {

    Movie insert(Movie movie);

    Optional<Movie> get(String movieId);

    Iterator<Movie> getAll(Optional<Integer> limit);

    Iterator<Movie> getAllByName(Optional<String> startString, Optional<String> endString, Optional<Integer> limit);

    PagedMovies getAllPaged(Long startToken, Integer limit);

}
