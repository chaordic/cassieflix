package br.com.chaordic.cassieflix.core.pojo;

import java.util.List;

public class PagedMovies {
	
	private Long startToken;
	private Long endToken;
	private List<Movie> movies;
	
	public PagedMovies(Long startToken, Long endToken, List<Movie> movies) {
		this.startToken = startToken;
		this.endToken = endToken;
		this.movies = movies;
	}
	
	public Long getStartToken() {
		return startToken;
	}
	public Long getEndToken() {
		return endToken;
	}
	public List<Movie> getMovies() {
		return movies;
	}
	
	
}
