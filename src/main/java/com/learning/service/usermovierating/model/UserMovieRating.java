package com.learning.service.usermovierating.model;

import java.util.List;

public class UserMovieRating {
	
	private String userId;
	private List<MovieRating> listMovie;

	public List<MovieRating> getListMovie() {
		return listMovie;
	}

	public void setListMovie(List<MovieRating> listMovie) {
		this.listMovie = listMovie;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

}
