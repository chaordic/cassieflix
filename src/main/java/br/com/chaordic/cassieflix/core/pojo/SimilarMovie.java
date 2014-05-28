package br.com.chaordic.cassieflix.core.pojo;


public class SimilarMovie {

    private String movieId;
    private Double similarity;
    private String similarMovieId;
    
    public String getMovieId() {
        return movieId;
    }
    
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    
    public Double getSimilarity() {
        return similarity;
    }
    
    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
    
    public String getSimilarMovieId() {
        return similarMovieId;
    }
    
    public void setSimilarMovieId(String similarMovieId) {
        this.similarMovieId = similarMovieId;
    }
}
