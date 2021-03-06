package br.com.chaordic.cassieflix.core.pojo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MovieList {

    private UUID id;
    private String title;
    private String owner;
    private Date created;
    private List<MovieSummary> movies;
    
    public UUID getId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
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
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }    
    
    public Date getCreated() {
        return created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }
    
    public List<MovieSummary> getMovies() {
        return movies;
    }
    
    public void setMovies(List<MovieSummary> movies) {
        this.movies = movies;
    }
}
