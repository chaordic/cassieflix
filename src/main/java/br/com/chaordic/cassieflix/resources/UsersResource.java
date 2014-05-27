package br.com.chaordic.cassieflix.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.chaordic.cassieflix.core.pojo.Message;
import br.com.chaordic.cassieflix.core.pojo.MovieListSummary;
import br.com.chaordic.cassieflix.core.pojo.User;
import br.com.chaordic.cassieflix.db.dao.MovieListDao;
import br.com.chaordic.cassieflix.db.dao.UserDao;

import com.codahale.metrics.annotation.Timed;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private static final Message USER_NOT_FOUND = new Message("User not found!");
    private static final Message USER_EXISTS = new Message("User with the same id already exists."
            + " If you wish to update an user use "
            + "PUT instead.");
    
    private UserDao userDao;
    private MovieListDao movieListDao;

    public UsersResource(UserDao userDao, MovieListDao movieListDao) {
        this.userDao = userDao;
        this.movieListDao = movieListDao;
    }

    @POST
    @Timed
    public Response createUser(User user) {
        if (user.getCreationDate() == null) {
            user.setCreationDate(new Date());
        }

        if (userDao.updateUser(user, true)) {
            return Response.ok(user).build();            
        }

        return Response.status(Status.FORBIDDEN).entity(USER_EXISTS).build();  
    }

    @PUT
    @Timed
    @Path("{userId}")    
    public Response updateUser(@PathParam("userId") String login, User user) {
        if (!login.equals(user.getLogin())) {
            Message errorMsg = new Message(String.format("Request login %s different from user login %s.", login,
                    user.getLogin()));
            return Response.status(Status.BAD_REQUEST).entity(errorMsg).build();              
        }

        if (user.getCreationDate() == null) {
            user.setCreationDate(new Date());
        }

        userDao.updateUser(user, false);

        return Response.ok(user).build();
    }    

    @GET
    @Timed
    @Path("{userId}")
    public Response getUser(@PathParam("userId") String login) {
        User user = userDao.getUser(login);

        if (user != null) {
            return Response.ok(user).build();            
        }

        return Response.status(Response.Status.NOT_FOUND).entity(USER_NOT_FOUND).build();
    }

    @GET
    @Timed
    @Path("{userLogin}/movieList")
    public Response getMovie(@PathParam("userLogin") String userLogin) {
        List<MovieListSummary> userList = movieListDao.getMovieListsByUser(userLogin);
        return Response.ok(userList).build();
    }     
}