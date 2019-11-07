package com.learning.service.usermovierating.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.service.usermovierating.model.MovieRating;
import com.learning.service.usermovierating.model.UserMovieRating;


@RestController
@RequestMapping("/api/v1/movie-ratings")
public class RatingController {

	Logger logger = LoggerFactory.getLogger(RatingController.class);
	
	@RequestMapping("/{userId}")
	public UserMovieRating getMovieRating(@PathVariable(name = "userId") String userId) {
		
		logger.info("Rating Controller is called for {} ", userId);
		
		UserMovieRating userMovieRating = new UserMovieRating();
		userMovieRating.setUserId(userId);
		
		MovieRating movieRating1 = new MovieRating(userId, "1", 9);
		MovieRating movieRating2 = new MovieRating(userId, "2",  6);
		
		List<MovieRating> ratings = Arrays.asList(movieRating1, movieRating2);
		
		userMovieRating.setListMovie(ratings);
		return userMovieRating;
	}
}
