package br.com.chaordic.cassieflix.resources;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.chaordic.cassieflix.core.cassandra.CassandraClient;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Maps;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {

    private CassandraClient cassieClient;

	public StatusResource(CassandraClient cassieClient) {
		this.cassieClient = cassieClient;
	}

	@GET
    @Timed
    public HashMap<String, Object> getStatus() {
		HashMap<String, Object> status = Maps.newHashMap();
		status.put("cassandraStatus", cassieClient.getClusterStatus());
        return status;
    }
}