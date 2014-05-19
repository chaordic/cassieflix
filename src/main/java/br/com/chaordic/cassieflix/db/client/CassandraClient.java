package br.com.chaordic.cassieflix.db.client;

import java.util.Set;

import br.com.chaordic.cassieflix.db.ClusterStatus;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;

public interface CassandraClient {

	ClusterStatus getClusterStatus();

	Set<Host> getAllHosts();

	Session getSession();

}
