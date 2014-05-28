package br.com.chaordic.cassieflix.db.dao.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

import java.util.LinkedList;
import java.util.List;

import br.com.chaordic.cassieflix.core.pojo.SimilarMovie;
import br.com.chaordic.cassieflix.db.Tables;
import br.com.chaordic.cassieflix.db.client.CassandraClient;
import br.com.chaordic.cassieflix.db.dao.SimilarMoviesDao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.Select.Where;

public class SimilarMoviesCassandraDao extends AbstractCassandraDao implements SimilarMoviesDao {

    public SimilarMoviesCassandraDao(CassandraClient client) {
        super(client);
    }

    private static final String SIMILAR_MOVIES = Tables.similarmovies.name();
    
    @Override
    public void insert(SimilarMovie similarMovie) {
        Insert insertSimilarMovie = insertInto(SIMILAR_MOVIES)
                .value("movieid", similarMovie.getMovieId())
                .value("similarmovieid", similarMovie.getSimilarMovieId())
                .value("similarity", similarMovie.getSimilarity());

        execute(insertSimilarMovie);
    }
    
    @Override    
    public List<String> getSimilarMovies(String movieId) {
        Where selectSimilarMovies = select().column("similarmovieid").
                                        from(SIMILAR_MOVIES).where(eq("movieid", movieId));
        
        
        LinkedList<String> similarMovieIds = new LinkedList<String>();        
        ResultSet resultSet = execute(selectSimilarMovies);
        for (Row row : resultSet) {
            similarMovieIds.add(row.getString("similarmovieid"));
        }
        
        return similarMovieIds;
    }

}
