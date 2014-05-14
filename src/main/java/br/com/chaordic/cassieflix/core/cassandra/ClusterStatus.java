package br.com.chaordic.cassieflix.core.cassandra;

import java.util.List;

import com.datastax.driver.core.ConsistencyLevel;

public class ClusterStatus {

	private String clusterName;	
	private ConsistencyLevel consistencyLevel;
	private List<String> hosts;	

	public String getClusterName() {
		return clusterName;
	}	
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public ConsistencyLevel getConsistencyLevel() {
		return consistencyLevel;
	}

	public void setConsistencyLevel(ConsistencyLevel consistencyLevel) {
		this.consistencyLevel = consistencyLevel;
	}	

	public List<String> getHosts() {
		return hosts;
	}	
	
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}
}
