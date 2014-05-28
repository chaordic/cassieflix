package br.com.chaordic.cassieflix.resources;

import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.chaordic.cassieflix.core.pojo.ActionType;
import br.com.chaordic.cassieflix.core.pojo.UserActivity;
import br.com.chaordic.cassieflix.db.dao.UserActivityDao;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.utils.UUIDs;

@Path("/userActivity")
@Produces(MediaType.APPLICATION_JSON)
public class UserActivityResource {

    private UserActivityDao userActivityDao;

	public UserActivityResource(UserActivityDao userActivityDao) {
		this.userActivityDao = userActivityDao;
	}

    @POST
    @Timed
    public Response postUserActivity(UserActivity userActivity) {
        userActivity.setId(UUIDs.timeBased());
        userActivityDao.insert(userActivity);
        
        return Response.ok(userActivity).build();
    }

    @GET
    @Timed
    @Path("{userId}/{type}")
    public Response getActivity(@PathParam("userId") String userId, @PathParam("type") ActionType type) {        
        Iterator<UserActivity> userActivities = userActivityDao.getUserActivities(userId, type);
        
        return Response.ok(userActivities).build();
    }
}