package br.com.chaordic.cassieflix.db.dao;

import br.com.chaordic.cassieflix.core.pojo.User;

public interface UserDao {

    boolean updateUser(User user, boolean ifNotExists);
        
    User getUser(String login);

}
