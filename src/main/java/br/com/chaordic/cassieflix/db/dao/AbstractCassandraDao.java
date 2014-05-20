package br.com.chaordic.cassieflix.db.dao;

import br.com.chaordic.cassieflix.db.client.CassandraClient;

import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractCassandraDao {

	protected Session session;
	ObjectMapper mapper;

	public AbstractCassandraDao(CassandraClient client) {
		this.session = client.getSession();
		mapper = new ObjectMapper();
	}
}
