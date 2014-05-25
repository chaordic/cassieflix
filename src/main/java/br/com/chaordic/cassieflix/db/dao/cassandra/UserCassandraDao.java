package br.com.chaordic.cassieflix.db.dao.cassandra;

import static br.com.chaordic.cassieflix.db.Tables.usersbyid;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.Map;
import java.util.Map.Entry;

import br.com.chaordic.cassieflix.core.pojo.User;
import br.com.chaordic.cassieflix.core.util.MarshallUtils;
import br.com.chaordic.cassieflix.db.client.CassandraClient;
import br.com.chaordic.cassieflix.db.dao.UserDao;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.Select;

public class UserCassandraDao extends AbstractCassandraDao implements UserDao {

    private static final String USER_LOGIN = "login";
    private static final String USERS_BY_ID = usersbyid.name();

    public UserCassandraDao(CassandraClient client) {
        super(client);
    }

    @Override
    public boolean updateUser(User user, boolean ifNotExists) {
        Map<String, Object> userMap = MarshallUtils.marshall(user);
        Insert insertUser = insertInto(USERS_BY_ID);
        for (Entry<String, Object> entry : userMap.entrySet()) {
            insertUser.value(entry.getKey(), entry.getValue());
        }
        if (ifNotExists) {
            insertUser.ifNotExists();            
        }

        Row result = session.execute(insertUser).one();
        if (result != null){
            return result.getBool(0);
        }

        return true;
    }

    @Override
    public User getUser(String login) {
        Select.Where selectUser = select().all().from(USERS_BY_ID).where(eq(USER_LOGIN, login));
        Row resultUser = session.execute(selectUser).one();
        if (resultUser != null) {
            return MarshallUtils.unmarshall(resultUser, User.class);
        }
        return null;
    }

}
