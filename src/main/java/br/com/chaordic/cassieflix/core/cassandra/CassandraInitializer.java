package br.com.chaordic.cassieflix.core.cassandra;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.datastax.driver.core.Cluster.Initializer;
import com.datastax.driver.core.Configuration;
import com.datastax.driver.core.Host.StateListener;
import com.datastax.driver.core.ProtocolOptions;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class CassandraInitializer implements Initializer {

	private static final int DEFAULT_PORT = ProtocolOptions.DEFAULT_PORT;
	List<InetSocketAddress> contactPoints;

	@NotEmpty
	@JsonProperty
	List<String> seeds;

	@JsonProperty	
	String clusterName = "default";

	public void setContactPoints(List<InetSocketAddress> contactPoints) {
		this.contactPoints = contactPoints;
	}	

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	@Override
	public List<InetSocketAddress> getContactPoints() {
		return contactPoints;
	}	

	@Override
	public String getClusterName() {
		return clusterName;
	}

	@Override
	public Configuration getConfiguration() {
		return new Configuration();
	}

	@Override
	public Collection<StateListener> getInitialListeners() {
		return Collections.emptyList();
	}

	public void setSeeds(List<String> seeds) {
		this.seeds = seeds;
		this.contactPoints = Lists.transform(seeds, new Function<String, InetSocketAddress>(){
			@Override
			public InetSocketAddress apply(String seedAddress) {
	            try {
	                return new InetSocketAddress(InetAddress.getByName(seedAddress), DEFAULT_PORT);
	            } catch (UnknownHostException e) {
	                throw new IllegalArgumentException(e.getMessage());
	            }
			}
		});
	}	

}
