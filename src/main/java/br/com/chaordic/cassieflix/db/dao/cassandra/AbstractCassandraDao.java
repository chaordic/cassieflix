package br.com.chaordic.cassieflix.db.dao.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.chaordic.cassieflix.db.client.CassandraClient;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

public abstract class AbstractCassandraDao {

    protected Session session;
    private static final Logger logger = LoggerFactory.getLogger(AbstractCassandraDao.class);
    
    public AbstractCassandraDao(CassandraClient client) {
        this.session = client.getSession();
    }
    
    public ResultSet execute(Statement statement) {
        logger.info("Executing: " + statement);
        return this.session.execute(statement);
    }
}
