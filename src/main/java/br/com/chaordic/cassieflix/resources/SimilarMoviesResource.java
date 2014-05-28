package br.com.chaordic.cassieflix.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.chaordic.cassieflix.core.pojo.Movie;
import br.com.chaordic.cassieflix.core.pojo.SimilarMovie;
import br.com.chaordic.cassieflix.db.dao.MovieDao;
import br.com.chaordic.cassieflix.db.dao.SimilarMoviesDao;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/similarMovies")
@Produces(MediaType.APPLICATION_JSON)
public class SimilarMoviesResource {

    private MovieDao movieDao;
    private SimilarMoviesDao similarMoviesDao;

	public SimilarMoviesResource(MovieDao movieDao, SimilarMoviesDao similarMoviesDao) {
		this.movieDao = movieDao;
		this.similarMoviesDao = similarMoviesDao;
	}

    @POST
    @Timed
    public Response insertSimilarMovie(SimilarMovie similarMovie) {
        similarMoviesDao.insert(similarMovie);
        
        return Response.ok(similarMovie).build();
    }

    @GET
    @Timed
    @Path("{movieId}")
    public Response getSimilarMovies(@PathParam("movieId") String movieId) {
        ArrayList<Movie> similarMovies = new ArrayList<Movie>();
        
        List<String> similarMovieIds = similarMoviesDao.getSimilarMovies(movieId);
        for (String similarMovieId : similarMovieIds) {
            Optional<Movie> similarMovie = movieDao.get(similarMovieId);
            if (similarMovie.isPresent()) {
                similarMovies.add(similarMovie.get());
            }
        }
        
        
        return Response.ok(similarMovies).build();
    }
}