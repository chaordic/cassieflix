package br.com.chaordic.cassieflix.db.dao;

import java.util.List;
import java.util.UUID;

import br.com.chaordic.cassieflix.core.pojo.MovieList;
import br.com.chaordic.cassieflix.core.pojo.MovieListSummary;

import com.google.common.base.Optional;

public interface MovieListDao {

    void insert(MovieList movieList);

    Optional<MovieList> get(UUID listId);
    
    List<MovieListSummary> getMovieListsByUser(String login);
}
