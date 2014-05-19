package br.com.chaordic.cassieflix.db.client;

import java.util.List;
import java.util.Set;

import br.com.chaordic.cassieflix.db.CassandraInitializer;
import br.com.chaordic.cassieflix.db.ClusterStatus;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class SyncCassandraClient implements CassandraClient {

	private static final String CASSIEFLIX_KS = "cassieflix";
    private static final String DOWN = "DOWN";
	private static final String UP = "UP";
	private Cluster cluster;
	private Session session;

	public SyncCassandraClient(CassandraInitializer initializer) {
		cluster = Cluster.buildFrom(initializer);
		session = cluster.connect(CASSIEFLIX_KS);
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

    @Override
    public Session getSession() {
        return session;
    }
}
