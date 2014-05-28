package br.com.chaordic.cassieflix.db.dao.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.Iterator;
import java.util.UUID;

import br.com.chaordic.cassieflix.core.pojo.ActionType;
import br.com.chaordic.cassieflix.core.pojo.UserActivity;
import br.com.chaordic.cassieflix.db.Tables;
import br.com.chaordic.cassieflix.db.client.CassandraClient;
import br.com.chaordic.cassieflix.db.dao.UserActivityDao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;

public class UserActivityCassandraDao extends AbstractCassandraDao implements UserActivityDao {

    public UserActivityCassandraDao(CassandraClient client) {
        super(client);
    }

    private static final String USER_ACTIVITY_BY_USER = Tables.useractivitybyuser.name();
    
    @Override
    public void insert(UserActivity userActivity) {
        Insert insertUserActivity = insertInto(USER_ACTIVITY_BY_USER)
                .value("userid", userActivity.getLogin())
                .value("activitytype", userActivity.getType().name())
                .value("activityid", userActivity.getId())
                .value("activitymovieid", userActivity.getMovieId());

        execute(insertUserActivity);
    }
    
    @Override    
    public Iterator<UserActivity> getUserActivities(final String userId, final ActionType type) {
        Where selectUserActivity = select("activityid", "activitymovieid")
                   .from(USER_ACTIVITY_BY_USER).where(eq("userid", userId))
                   .and(eq("activitytype", type.name()));
        ResultSet result = execute(selectUserActivity);
        
        return Iterators.transform(result.iterator(), new Function<Row, UserActivity>() {

            @Override
            public UserActivity apply(Row row) {
                UUID activityId = row.getUUID("activityid");
                String movieId = row.getString("activitymovieid");           
                return new UserActivity(activityId, userId, type, movieId);
            }
        });
    }

}
