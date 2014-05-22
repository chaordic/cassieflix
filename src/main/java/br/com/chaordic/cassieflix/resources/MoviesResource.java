package br.com.chaordic.cassieflix.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.chaordic.cassieflix.core.dao.MovieDao;
import br.com.chaordic.cassieflix.core.pojo.Movie;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {

    private static final String MOVIE_NOT_FOUND = "Movie not found!";
    private MovieDao movieDao;

	public MoviesResource(MovieDao movieDao) {
		this.movieDao = movieDao;
	}

    @POST
    @Timed
    public Response insertMovie(Movie movie) {
        movieDao.insert(movie);
        return Response.ok(movie).build();
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

    @GET
    @Timed
    public Response getAll(@QueryParam("limit") Optional<Integer> limit,
                            @QueryParam("startToken") Optional<Long> startToken,
                            @QueryParam("startString") Optional<String> startString,
                            @QueryParam("endString") Optional<String> endString,
                            @QueryParam("unordered") Optional<Boolean> unordered) {
        if (startToken.isPresent()) {
            Integer numResults = limit.or(100);
            return Response.ok(movieDao.getAllPaged(startToken.get(), numResults)).build();
        }

        if (unordered.or(false)){
            return Response.ok(movieDao.getAll(limit)).build();
        }

        return Response.ok(movieDao.getAllByName(startString, endString, limit)).build();
    }
}