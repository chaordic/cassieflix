package br.com.chaordic.cassieflix.core.pojo;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;

public class UserActivity {
    
    private UUID id;
    private String login;
    private String movieId;
    private ActionType type;
    private Date date = null;

    public UserActivity(UUID id, String userId, ActionType type, String movieId) {
        this.id = id;
        this.login = userId;
        this.type = type;
        this.movieId = movieId;
    }
    
    public UserActivity(){
        //empty
    }

    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getMovieId() {
        return movieId;
    }
    
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    
    public ActionType getType() {
        return type;
    }
    
    public void setType(ActionType type) {
        this.type = type;
    }

    public Date getDate(){
        if (date == null && getId() != null ) {
            date = new Date(UUIDs.unixTimestamp(getId()));
        }
        return date;
    }
    
}
