package br.com.chaordic.cassieflix.core.dao;

import java.util.Iterator;

import br.com.chaordic.cassieflix.core.pojo.Movie;

import com.google.common.base.Optional;

public interface MovieDao {

    Movie insert(Movie movie);

    Optional<Movie> get(String movieId);

    Iterator<Movie> getAll();

}
