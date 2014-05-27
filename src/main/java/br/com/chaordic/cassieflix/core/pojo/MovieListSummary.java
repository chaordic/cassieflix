package br.com.chaordic.cassieflix.core.pojo;

import java.util.Date;
import java.util.UUID;


public class MovieListSummary {

    private UUID id;
    private String title;
    private Date created;
    
    public MovieListSummary(){
        
    }
    
    public MovieListSummary(UUID id, String title, Date created) {
        this.id = id;
        this.title = title;
        this.created = created;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }   
}
