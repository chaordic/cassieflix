package br.com.chaordic.cassieflix.db.dao;

import static br.com.chaordic.cassieflix.core.util.MarshallUtils.marshall;
import static br.com.chaordic.cassieflix.core.util.MarshallUtils.unmarshall;
import static br.com.chaordic.cassieflix.db.Tables.movies;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import br.com.chaordic.cassieflix.core.dao.MovieDao;
import br.com.chaordic.cassieflix.core.dao.PagedMovies;
import br.com.chaordic.cassieflix.core.pojo.Movie;
import br.com.chaordic.cassieflix.db.client.CassandraClient;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public class MovieCassandraDao extends AbstractCassandraDao implements MovieDao {

    private static final String MOVIES_TABLE = movies.name();

    public MovieCassandraDao(CassandraClient client) {
        super(client);
    }

    @Override
    public Movie insert(Movie movie) {
        Map<String, Object> movieObj = marshall(movie);

        Insert insertInto = QueryBuilder.insertInto(MOVIES_TABLE);
        for (Entry<String, Object> property : movieObj.entrySet()) {
            insertInto.value(property.getKey(), property.getValue());
        }
        session.execute(insertInto);
        return movie;
    }

    @Override
    public Optional<Movie> get(String movieId) {
        Where selectWhere = QueryBuilder.select().all().from(MOVIES_TABLE).where(eq("id", movieId));
        ResultSet result = session.execute(selectWhere);

        Iterator<Row> it = result.iterator();
        if (it.hasNext()) {
            Movie movie = unmarshall(it.next(), Movie.class);
            return Optional.of(movie);
        }

        return Optional.absent();
    }

    @Override
    public Iterator<Movie> getAll(Optional<Integer> limit) {
        Select select = QueryBuilder.select().all().from(MOVIES_TABLE);
        if (limit.isPresent()) {
            select.limit(limit.get());
        }
        ResultSet result = session.execute(select);

        return moviesIterator(result);
    }

    private Iterator<Movie> moviesIterator(ResultSet result) {
        return Iterators.transform(result.iterator(), new Function<Row, Movie>() {
            @Override
            public Movie apply(Row row) {
                return unmarshall(row, Movie.class);
            }
        });
    }

    @Override
    public PagedMovies getAllPaged(Long startToken, Integer limit) {
        Select.Where select = QueryBuilder.select().all().from(MOVIES_TABLE).limit(limit)
                .where(gt(token("id"), startToken));
        ResultSet result = session.execute(select);

        Iterator<Movie> moviesIterator = moviesIterator(result);
        ArrayList<Movie> resultList = Lists.newArrayList(moviesIterator);

        Movie lastMoview = resultList.get(resultList.size()-1);
        Select.Where select2 = QueryBuilder.select().column("token(id)").from(MOVIES_TABLE)
                .where(eq("id", lastMoview.getId()));
        ResultSet result2 = session.execute(select2);
        Long endToken = result2.one().getLong(0);

        return new PagedMovies(startToken, endToken, resultList);
    }
}
