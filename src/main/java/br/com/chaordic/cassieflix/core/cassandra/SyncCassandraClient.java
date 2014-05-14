package br.com.chaordic.cassieflix.core.cassandra;

import java.util.List;
import java.util.Set;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


public class SyncCassandraClient implements CassandraClient {

	private static final String DOWN = "DOWN";
	private static final String UP = "UP";
	private Cluster cluster;

	public SyncCassandraClient(CassandraInitializer initializer) {
		cluster = Cluster.buildFrom(initializer);
	}

	public void close() {
		cluster.close();
	}

	@Override
	public ClusterStatus getClusterStatus() {
		ClusterStatus status = new ClusterStatus();
		status.setClusterName(cluster.getClusterName());
		status.setHosts(getPrettyHosts(cluster.getMetadata().getAllHosts()));
		status.setConsistencyLevel(cluster.getConfiguration().getQueryOptions().getConsistencyLevel());
		return status;
	}

	private List<String> getPrettyHosts(Set<Host> allHosts) {
		return Lists.newArrayList(Iterables.transform(allHosts, new Function<Host, String>() {
			@Override
			public String apply(Host host) {
				return String.format("%s|%s", host.getSocketAddress(), host.isUp()? UP : DOWN);
			}
		}));
	}

	@Override
	public Set<Host> getAllHosts() {
		return cluster.getMetadata().getAllHosts();
	}	
}
