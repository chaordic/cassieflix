package br.com.chaordic.cassieflix.db.dao;

import java.util.List;

import br.com.chaordic.cassieflix.core.pojo.SimilarMovie;

public interface SimilarMoviesDao {

    public void insert(SimilarMovie similarMovie);
    
    public List<String> getSimilarMovies(String movieId);    

}
