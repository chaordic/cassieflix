package br.com.chaordic.cassieflix.core.pojo;


public class MovieSummary {

    private String id;
    private String title;
    private Integer year;
    private String poster;

    public MovieSummary(String id, String title, Integer year, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.poster = poster;
    }
    
    public MovieSummary(){
        
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
}
