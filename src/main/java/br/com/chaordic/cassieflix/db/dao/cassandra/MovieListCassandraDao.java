package br.com.chaordic.cassieflix.db.dao.cassandra;

import static br.com.chaordic.cassieflix.db.Tables.movielistbyid;
import static com.datastax.driver.core.querybuilder.QueryBuilder.batch;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import br.com.chaordic.cassieflix.core.pojo.MovieList;
import br.com.chaordic.cassieflix.core.pojo.MovieSummary;
import br.com.chaordic.cassieflix.db.client.CassandraClient;
import br.com.chaordic.cassieflix.db.dao.MovieListDao;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;

public class MovieListCassandraDao extends AbstractCassandraDao implements MovieListDao {

    private static final String MOVIE_LIST_BY_ID = movielistbyid.name();

    public MovieListCassandraDao(CassandraClient client) {
        super(client);
    }

    @Override
    public void insert(MovieList movieList) {
        Insert staticMovieList = insertInto(MOVIE_LIST_BY_ID)
        .value("listid", movieList.getId())
        .value("listtitle", movieList.getTitle());
        
        Batch insertMovieList = batch(staticMovieList);
        Integer moviePosition = 0;
        for (MovieSummary summary : movieList.getMovies()) {
            Insert movieSummary = insertInto(MOVIE_LIST_BY_ID)
                    .value("listid", movieList.getId())
                    .value("movieposition", moviePosition++)
                    .value("movieid", summary.getId())
                    .value("movietitle", summary.getTitle())
                    .value("movieyear", summary.getYear());
            insertMovieList.add(movieSummary);
        }
        
        session.execute(insertMovieList);
    }


}
