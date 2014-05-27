package br.com.chaordic.cassieflix.db.dao.cassandra;

import static br.com.chaordic.cassieflix.db.Tables.movielistbyid;
import static com.datastax.driver.core.querybuilder.QueryBuilder.batch;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import br.com.chaordic.cassieflix.core.pojo.MovieList;
import br.com.chaordic.cassieflix.core.pojo.MovieListSummary;
import br.com.chaordic.cassieflix.core.pojo.MovieSummary;
import br.com.chaordic.cassieflix.db.Tables;
import br.com.chaordic.cassieflix.db.client.CassandraClient;
import br.com.chaordic.cassieflix.db.dao.MovieListDao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.google.common.base.Optional;

public class MovieListCassandraDao extends AbstractCassandraDao implements MovieListDao {

    private static final String MOVIE_LIST_BY_ID = movielistbyid.name();
    private static final String MOVIE_LIST_BY_USER = Tables.movielistsummarybyuser.name();

    public MovieListCassandraDao(CassandraClient client) {
        super(client);
    }

    @Override
    public void insert(MovieList movieList) {
        Batch insertMovieList = insertMovieById(movieList);
        
        Insert insertMovieListByUser = insertInto(MOVIE_LIST_BY_USER)
        .value("userlogin", movieList.getOwner())
        .value("listcreation", movieList.getCreated())
        .value("listid", movieList.getId())
        .value("listtitle", movieList.getTitle());
        
        insertMovieList.add(insertMovieListByUser);
        
        execute(insertMovieList);
    }

    private Batch insertMovieById(MovieList movieList) {
        Insert staticMovieList = insertInto(MOVIE_LIST_BY_ID)
        .value("listid", movieList.getId())
        .value("listtitle", movieList.getTitle())
        .value("listowner", movieList.getOwner())
        .value("listcreated", movieList.getCreated());
        
        Batch insertMovieList = batch(staticMovieList);
        Integer moviePosition = 0;
        for (MovieSummary summary : movieList.getMovies()) {
            Insert movieSummary = insertInto(MOVIE_LIST_BY_ID)
                    .value("listid", movieList.getId())
                    .value("movieposition", moviePosition++)
                    .value("movieid", summary.getId())
                    .value("movieposter", summary.getPoster())
                    .value("movietitle", summary.getTitle())
                    .value("movieyear", summary.getYear());
            insertMovieList.add(movieSummary);
        }
        return insertMovieList;
    }

    @Override
    public Optional<MovieList> get(UUID listId) {
        Where selectMovieList = select().all().from(MOVIE_LIST_BY_ID).where(eq("listid", listId));
        ResultSet result = execute(selectMovieList);
        
        MovieList movieList = new MovieList();
        movieList.setMovies(new LinkedList<MovieSummary>());
        boolean set = false;
        for (Row row : result) {
            if (!set) {
                movieList.setId(row.getUUID("listid"));
                movieList.setTitle(row.getString("listtitle"));
                movieList.setCreated(row.getDate("listcreated"));
                movieList.setOwner(row.getString("listowner"));
                set = true;
            }
            String movieId = row.getString("movieid");
            String movieTitle = row.getString("movietitle");
            Integer movieYear = row.getInt("movieyear");
            String moviePoster = row.getString("movieposter");
            movieList.getMovies().add(new MovieSummary(movieId, movieTitle, movieYear, moviePoster));
            
        }
        
        if (set) {
            return Optional.of(movieList);
        }
        
        return Optional.absent();
    }

    @Override
    public List<MovieListSummary> getMovieListsByUser(String login) {
        Where movieListByuser = select().all().from(MOVIE_LIST_BY_USER).where(eq("userlogin", login));
        ResultSet resultSet = execute(movieListByuser);
        
        LinkedList<MovieListSummary> result = new LinkedList<MovieListSummary>();
        for (Row row : resultSet) {
            UUID listId = row.getUUID("listid");
            Date creationDate = row.getDate("listcreation");
            String listTitle = row.getString("listtitle");
            result.add(new MovieListSummary(listId, listTitle, creationDate));
        }
        
        return result;
    }


}
