package br.com.chaordic.cassieflix.db.dao;

import static br.com.chaordic.cassieflix.db.Tables.movies;

import java.util.Map;
import java.util.Map.Entry;

import br.com.chaordic.cassieflix.core.dao.MovieDao;
import br.com.chaordic.cassieflix.core.pojo.Movie;
import br.com.chaordic.cassieflix.db.client.CassandraClient;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;

public class MovieCassandraDao extends AbstractCassandraDao implements MovieDao {

    public MovieCassandraDao(CassandraClient client) {
        super(client);
    }

    @Override
    public Movie insert(Movie movie) {
        Map<String, Object> movieObj = describe(movie);

        Insert insertInto = QueryBuilder.insertInto(movies.name());
        for (Entry<String, Object> property : movieObj.entrySet()) {
            insertInto.value(property.getKey(), property.getValue());
        }
        session.execute(insertInto);
        return movie;
    }

    @Override
    public Optional<Movie> get(String movieId) {
        return Optional.absent();
    }
}
