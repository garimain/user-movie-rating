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
import com.learning.service.usermovierating.model.MovieBO;
import com.learning.service.usermovierating.model.MovieInfoSearchResponseBO;
import com.learning.service.usermovierating.model.MovieRatingBO;
import com.learning.service.usermovierating.model.UserMovieRatingSearchBO;
import com.learning.service.usermovierating.repository.UserMovieRatingRepository;
import com.learning.service.usermovierating.repository.model.UserMovieRating;
import com.learning.service.usermovierating.service.proxy.MovieInfoProxy;

@Service
public class UserMovieRatingService {
	
	Logger logger = LoggerFactory.getLogger(UserMovieRatingService.class);
	
	@Autowired
	private UserMovieRatingRepository userMovieRatingRepository;
	
	@Autowired
	private MovieInfoProxy movieInfoProxy;
	
	public List<MovieRatingBO> addUserMovieRating(String userId, List<MovieRatingBO> movieRatingList) {
		
		logger.info("addUserMovieRating -  is called with userid as  {} ", userId);
		
		List<Integer> movieIds = movieRatingList.stream().map(r->r.getMovieId()).collect(Collectors.toList());
		
		boolean valid = validateMovieIds(movieIds);
		
		if (valid) {
			
			logger.info("addUserMovieRating -  is called with userid as  {} with all valid movieIds ", userId);
			
			Date currentDatetime = new Date();
			List<UserMovieRating> userMovieRating = movieRatingList.stream().
				map(r->new UserMovieRating(null, Integer.valueOf(userId), r.getMovieId(), r.getRating(), currentDatetime)).
				collect(Collectors.toList());
				
		
		
			List<UserMovieRating> savedUserMovieRating = userMovieRatingRepository.saveAll(userMovieRating);
		
		
			if (savedUserMovieRating != null) {
				logger.info("addUserMovieRating - Movie Ratings saved for user {} successfully ", userId);
				List<MovieRatingBO> savedMovieRatings = savedUserMovieRating.stream().
						map(r->new MovieRatingBO( r.getRatingId(), r.getMovieId(), r.getRating())).
						collect(Collectors.toList());
				return savedMovieRatings;
				
			} else {
				throw new UserMovieRatingNotAddedException("UserMovieRating");
			}
		} else {
			logger.info("addUserMovieRating -  is called with userid as  {} with some invalid movieIds ", userId);
			
			throw new UserMovieRatingNotAddedException("MovieId Provided not valid");
		}
		
	}
	
	
	public List<MovieRatingBO> getUserMovieRatings(UserMovieRatingSearchBO userMovieRatingSearchBO)  {
		
		logger.info("getUserMovieRatings - is called for retrieval with userid as  {} and movieId {} ", userMovieRatingSearchBO.getUserId(), userMovieRatingSearchBO.getMovieId());
		
		Integer userId = Integer.valueOf(userMovieRatingSearchBO.getUserId());
		Integer movieId = null;
		Optional<List<UserMovieRating>> userMovieRatingOptional;
		if (userMovieRatingSearchBO.getMovieId().isPresent()) {
			movieId = Integer.valueOf(userMovieRatingSearchBO.getMovieId().get());
		}
		
		if (movieId != null) {
			userMovieRatingOptional = userMovieRatingRepository.findByUserIdAndMovieId(userId, movieId);
			
		} else {
			userMovieRatingOptional = userMovieRatingRepository.findByUserId(userId);
		}
		
		
		
		if (userMovieRatingOptional.isPresent()) {
			List<UserMovieRating> movieRatingList = userMovieRatingOptional.get();
			
			List<MovieRatingBO> savedMovieRatings = movieRatingList.stream().
					map(r->new MovieRatingBO(r.getRatingId(), r.getMovieId(), r.getRating())).
					collect(Collectors.toList());
			return savedMovieRatings;
			
		} else {
			return null;
		}
	}
	
	
	private boolean validateMovieIds(List<Integer> movieIds) {
		
		logger.info("validateMovieIds - called with inputs {} ", movieIds);
		
		StringBuffer movieIdBuffer =new StringBuffer();
		
		movieIds.stream().forEach(i->movieIdBuffer.append(i.toString()).append(','));
		
		logger.info("validateMovieIds - movie info service buffer string {}", movieIdBuffer);
		
		String movieIdentiersStr = movieIdBuffer.toString().substring(0, movieIdBuffer.toString().length() -1);
		
		logger.info("validateMovieIds - movie info service will be called with inputs {} ", movieIdentiersStr);
				
		MovieInfoSearchResponseBO movieInfoSearchResponse = movieInfoProxy.getMovieDetails(movieIdentiersStr);
		
		if (movieInfoSearchResponse != null) {
			List<MovieBO> movieList = movieInfoSearchResponse.getMovieList();
		
			//Get all movie id's found from the search response
			List<Integer> movieIdFound = movieList.stream().map(m->m.getMovieId()).collect(Collectors.toList());
		
			return movieIdFound.containsAll(movieIds);
		} else {
			//if search is not successful then consider it is not valid
			return false;
		}
		
		
	}
	
	
	public void deleteUserMovieRating(Integer ratingId) {
		
		
		logger.info("deleteUserMovieRating -  is called for deletion with id as  {} ", ratingId);
		
		Optional<UserMovieRating> userMovieRatingOptional = userMovieRatingRepository.findById(ratingId);
		
		if (userMovieRatingOptional.isPresent()) {
		
			userMovieRatingRepository.deleteById(ratingId);
		} else {
			throw new UserMovieRatingNotFoundException("UserMovieRatingForDeleteNotFound");
		}
		
	}
	
}
