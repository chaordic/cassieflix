package br.com.chaordic.cassieflix.core.cassandra;

import java.util.Set;

import com.datastax.driver.core.Host;


public interface CassandraClient {

	ClusterStatus getClusterStatus();
	
	Set<Host> getAllHosts();
	
}
