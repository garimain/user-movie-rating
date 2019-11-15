package com.learning.service.usermovierating.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.service.usermovierating.common.exception.UserMovieRatingNotAddedException;
import com.learning.service.usermovierating.common.exception.UserMovieRatingNotFoundException;
import com.learning.service.usermovierating.model.MovieRatingBO;
import com.learning.service.usermovierating.repository.UserMovieRatingRepository;
import com.learning.service.usermovierating.repository.model.UserMovieRating;

@Service
public class UserMovieRatingService {
	
Logger logger = LoggerFactory.getLogger(UserMovieRatingService.class);
	
	@Autowired
	private UserMovieRatingRepository userMovieRatingRepository;
	
	
	public List<MovieRatingBO> addUserMovieRating(List<MovieRatingBO> movieRatingList) {
		
		Date currentDatetime = new Date();
		List<UserMovieRating> userMovieRating = movieRatingList.stream().
				map(r->new UserMovieRating(null, r.getUserId(), r.getMovieId(), r.getRating(), currentDatetime)).
				collect(Collectors.toList());
				
		
		
		List<UserMovieRating> savedUserMovieRating = userMovieRatingRepository.saveAll(userMovieRating);
		
		
		if (savedUserMovieRating != null) {
			logger.info("UserMovieRatingService saved user movie ratings successfully" );
			List<MovieRatingBO> savedMovieRatings = savedUserMovieRating.stream().
					map(r->new MovieRatingBO(r.getUserId(), r.getMovieId(), r.getRating())).
					collect(Collectors.toList());
			return savedMovieRatings;
			
		} else {
			throw new UserMovieRatingNotAddedException("UserMovieRating");
		}
		
		
	}
	
	
	public List<MovieRatingBO> getUserMovieRatings(Integer userId) throws UserMovieRatingNotFoundException {
		
		logger.info("UserMovieRatingService is called for retrieval with userid as  {} ", userId);
		
		Optional<List<UserMovieRating>> userMovieRatingOptional = userMovieRatingRepository.findByUserId(userId);
		
		if (userMovieRatingOptional.isPresent()) {
			List<UserMovieRating> movieRatingList = userMovieRatingOptional.get();
			
			List<MovieRatingBO> savedMovieRatings = movieRatingList.stream().
					map(r->new MovieRatingBO(r.getUserId(), r.getMovieId(), r.getRating())).
					collect(Collectors.toList());
			return savedMovieRatings;
			
		} else {
			throw new UserMovieRatingNotFoundException("MovieForSearhNotFound");
		}
	}
	
}
