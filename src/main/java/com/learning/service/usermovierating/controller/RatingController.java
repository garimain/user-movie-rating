package com.learning.service.usermovierating.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learning.service.usermovierating.model.MovieRatingBO;
import com.learning.service.usermovierating.model.UserMovieRatingBO;
import com.learning.service.usermovierating.repository.model.UserMovieRating;
import com.learning.service.usermovierating.service.UserMovieRatingService;


@RestController
@RequestMapping("/api/v1/movie-ratings")
public class RatingController {

	Logger logger = LoggerFactory.getLogger(RatingController.class);
	
	@Autowired
	private UserMovieRatingService userMovieRatingService;
	
	@RequestMapping("/{userId}")
	public UserMovieRatingBO getMovieRating(@PathVariable(name = "userId") String userId) {
		
		logger.info("Rating Controller is called to retrieve the movie rating for user id  {} ", userId);
		
		UserMovieRatingBO userMovieRating = new UserMovieRatingBO();
		userMovieRating.setUserId(userId);
		
		List<MovieRatingBO> movieRatings = userMovieRatingService.getUserMovieRatings(Integer.valueOf(userId));
		
		
		userMovieRating.setListMovie(movieRatings);
		return userMovieRating;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public UserMovieRatingBO addUserMovieRating(@RequestBody UserMovieRatingBO userMovieRatingBO) {
		
		logger.info("Rating Controller is called to save the movie rating for user id  {} ", userMovieRatingBO.getUserId());
		
		List<MovieRatingBO> savedMovieRatings = userMovieRatingService.addUserMovieRating(userMovieRatingBO.getListMovie());
		
		UserMovieRatingBO savedUserMovieRatingBO = new UserMovieRatingBO(userMovieRatingBO.getUserId(), savedMovieRatings);
		
		return savedUserMovieRatingBO;
		
		
	}
	
	
}
