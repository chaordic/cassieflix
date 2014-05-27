package br.com.chaordic.cassieflix.resources;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import br.com.chaordic.cassieflix.core.pojo.MovieListSummary;
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
    public Response insertMovieList(MovieList movieList) {
        if (movieList.getCreated() == null) {
            movieList.setCreated(new Date());
        }

        for (MovieSummary summary : movieList.getMovies()) {
            //lazy retrieval of movie summary
            if (summary.getTitle() == null) {
                String movieId = summary.getId();
                Optional<Movie> movie = movieDao.get(movieId);
                if (!movie.isPresent()) {
                    Message errorMsg = new Message(String.format("Movie %s not found", summary.getId()));
                    return Response.status(Response.Status.BAD_REQUEST).entity(errorMsg).build();                
                }
                populate(summary, movie.get());
            }
        }
        
        movieListDao.insert(movieList);
        
        return Response.ok(movieList).build();
    }

    @GET
    @Timed
    @Path("{movieListId}")
    public Response getMovie(@PathParam("movieListId") UUID movieListId) {
        Optional<MovieList> optional = movieListDao.get(movieListId);
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(MOVIE_NOT_FOUND).build();
    }

    
    private void populate(MovieSummary summary, Movie movie) {
        summary.setPoster(movie.getPoster());
        summary.setTitle(movie.getTitle());
        summary.setYear(movie.getYear());
    }
}