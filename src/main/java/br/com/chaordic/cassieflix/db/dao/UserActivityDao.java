package br.com.chaordic.cassieflix.db.dao;

import java.util.Iterator;

import br.com.chaordic.cassieflix.core.pojo.ActionType;
import br.com.chaordic.cassieflix.core.pojo.UserActivity;

public interface UserActivityDao {

    public void insert(UserActivity userActivity);

    public Iterator<UserActivity> getUserActivities(String userId, ActionType type);

}
