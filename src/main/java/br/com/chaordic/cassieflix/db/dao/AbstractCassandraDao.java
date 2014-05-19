package br.com.chaordic.cassieflix.db.dao;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import br.com.chaordic.cassieflix.db.client.CassandraClient;

import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

public abstract class AbstractCassandraDao {

    protected Session session;
    ObjectMapper mapper;

    public AbstractCassandraDao(CassandraClient client) {
        this.session = client.getSession();
        mapper = new ObjectMapper();
    }

    protected Map<String, Object> describe(Object pojo) {
        Map<String, Object> result = null;
        try {
            result = PropertyUtils.describe(pojo);
        } catch (Throwable t) {
            Throwables.propagate(t);
        }
        //remove unwanted properties inherited from Object
        result.remove("class");
        return result;
    }
}
