package br.com.chaordic.cassieflix.health;

import java.util.Set;

import br.com.chaordic.cassieflix.core.cassandra.CassandraClient;

import com.codahale.metrics.health.HealthCheck;
import com.datastax.driver.core.Host;

public class CassandraHealthCheck extends HealthCheck {
	private final CassandraClient client;

	public CassandraHealthCheck(CassandraClient client) {
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		Set<Host> allHosts = client.getAllHosts();

		if (allHosts.isEmpty())
		{
			Result.unhealthy("No hosts found.");
		}

		for (Host host : allHosts)
		{
			if (host.isUp())
			{
				return Result.healthy("At least one host UP. Host list: %s", allHosts);
			}
		}

		return Result.unhealthy("No healthy hosts UP. Host list: %s", allHosts);
	}
}
