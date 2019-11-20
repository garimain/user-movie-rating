package com.learning.service.usermovierating.controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learning.service.usermovierating.common.exception.UserMovieRatingMissingMandatoryFieldsException;
import com.learning.service.usermovierating.model.MovieRatingBO;
import com.learning.service.usermovierating.model.UserMovieRatingBO;
import com.learning.service.usermovierating.model.UserMovieRatingSearchBO;
import com.learning.service.usermovierating.service.UserMovieRatingService;


@RestController
@RequestMapping("/api/v1/movie-ratings")
public class RatingController {

	Logger logger = LoggerFactory.getLogger(RatingController.class);
	
	@Autowired
	private UserMovieRatingService userMovieRatingService;
	
	
	@RequestMapping(path = "/user-movie-rating", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public UserMovieRatingBO getMovieRating(@RequestParam(name = "userId", required = true ) String userId,
			@RequestParam(name = "movieId", required = false ) String movieId) {
		
		logger.info("Rating Controller - is called to retrieve the movie rating for user id  {} along with movie Id {}" , userId, movieId);
		
		//populate search criteria object with search parameters 
		UserMovieRatingSearchBO userMovieRatingSC = new UserMovieRatingSearchBO();
		
		if (userId != null) {
			userMovieRatingSC.setUserId(userId);
		} else {
			throw new UserMovieRatingMissingMandatoryFieldsException("UserId is mandatory for this interface");
		}
		
		userMovieRatingSC.setMovieId(Optional.ofNullable(movieId));
		
		
		List<MovieRatingBO> movieRatings = userMovieRatingService.getUserMovieRatings(userMovieRatingSC);
		
		UserMovieRatingBO userMovieRatingSR = new UserMovieRatingBO();
		userMovieRatingSR.setUserId(userId);
		if (movieRatings != null) {
			//After search populate search response
			userMovieRatingSR.setListMovie(movieRatings);
		} else {
			userMovieRatingSR.setListMovie(null);
		}
		return userMovieRatingSR;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/user-movie-rating")
	@ResponseStatus(HttpStatus.CREATED)
	public UserMovieRatingBO addUserMovieRating(@RequestBody UserMovieRatingBO userMovieRatingBO) {
		
		logger.info("Rating Controller - is called to save the movie rating for user id  {} ", userMovieRatingBO.getUserId());
		
		String userId = userMovieRatingBO.getUserId();
		
		if (userId == null) {
			throw new UserMovieRatingMissingMandatoryFieldsException("UserId is mandatory for this interface");
		}
		
		List<MovieRatingBO> savedMovieRatings = userMovieRatingService.addUserMovieRating(userId, userMovieRatingBO.getListMovie());
		
		UserMovieRatingBO savedUserMovieRatingBO = new UserMovieRatingBO(userMovieRatingBO.getUserId(), savedMovieRatings);
		
		return savedUserMovieRatingBO;
		
		
	}
	
	
	@RequestMapping(path = "/user-movie-rating/{ratingId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUserMovieRating(@PathVariable(name = "ratingId") String ratingId) {
		
		logger.info("Rating Controller - is called for deleting an existing user movie rating with id {}", ratingId);
		
		userMovieRatingService.deleteUserMovieRating(Integer.valueOf(ratingId));
		
		
	}
	
}
