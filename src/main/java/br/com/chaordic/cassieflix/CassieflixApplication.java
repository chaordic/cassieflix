package br.com.chaordic.cassieflix;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import br.com.chaordic.cassieflix.core.cassandra.CassandraClient;
import br.com.chaordic.cassieflix.core.cassandra.SyncCassandraClient;
import br.com.chaordic.cassieflix.health.CassandraHealthCheck;
import br.com.chaordic.cassieflix.resources.StatusResource;

public class CassieflixApplication extends Application<CassieflixConfiguration> {
	public static void main(String[] args) throws Exception {
		new CassieflixApplication().run(args);
	}

	@Override
	public String getName() {
		return "cassieflix";
	}

	@Override
	public void initialize(Bootstrap<CassieflixConfiguration> bootstrap) {
		// nothing to do yet
	}

	@Override
	public void run(CassieflixConfiguration conf, Environment env) {
		CassandraClient cassieClient = new SyncCassandraClient(conf.getCassandraInitializer());
		
		/* Health Checks */
		env.healthChecks().register("cassandra", new CassandraHealthCheck(cassieClient));
		
		/* Resources */		
	    env.jersey().register(new StatusResource(cassieClient));
	}

}
