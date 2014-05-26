package br.com.chaordic.cassieflix.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.chaordic.cassieflix.core.pojo.Message;
import br.com.chaordic.cassieflix.core.pojo.Movie;
import br.com.chaordic.cassieflix.core.pojo.MovieList;
import br.com.chaordic.cassieflix.core.pojo.MovieSummary;
import br.com.chaordic.cassieflix.db.dao.MovieDao;
import br.com.chaordic.cassieflix.db.dao.MovieListDao;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/movieList")
@Produces(MediaType.APPLICATION_JSON)
public class MovieListResource {

    private static final Message MOVIE_NOT_FOUND = new Message("Movie not found!");
    private MovieDao movieDao;
    private MovieListDao movieListDao;

	public MovieListResource(MovieDao movieDao, MovieListDao movieListDao) {
		this.movieDao = movieDao;
		this.movieListDao = movieListDao;
	}

    @POST
    @Timed
    @SuppressWarnings("unchecked")
    public Response insertMovieList(Map<String, Object> titleAndMovies) {
        String title = (String)titleAndMovies.get("title");
        List<String> movieIds = (List<String>)titleAndMovies.get("movies");

        ArrayList<MovieSummary> summaries = new ArrayList<MovieSummary>();
        for (String movieId : movieIds) {
            Optional<Movie> movie = movieDao.get(movieId);
            if (!movie.isPresent()) {
                Message errorMsg = new Message(String.format("Movie %s not found", movieId));
                return Response.status(Response.Status.BAD_REQUEST).entity(errorMsg).build();                
            }
            summaries.add(movie.get().getSummary());
        }
        MovieList movieList = new MovieList(title, summaries);
        
        movieListDao.insert(movieList);
        
        return Response.ok(movieList).build();
    }

    @GET
    @Timed
    @Path("{movieId}")
    public Response getMovie(@PathParam("movieId") String movieId) {
        Optional<Movie> optional = movieDao.get(movieId);
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(MOVIE_NOT_FOUND).build();
    }
}