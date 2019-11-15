package com.learning.service.usermovierating.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.service.usermovierating.model.MovieRatingBO;
import com.learning.service.usermovierating.repository.UserMovieRatingRepository;
import com.learning.service.usermovierating.repository.model.UserMovieRating;




@RunWith(SpringRunner.class)
public class UserMovieRatingServiceTest {
	
	@MockBean
	private UserMovieRatingRepository userMovieRatingRepository;
	
	@Autowired
	private UserMovieRatingService userMovieRatingService;
	
	
	@TestConfiguration
	public static class MovieInfoServiceConfiguration {
		
		@Bean
		public UserMovieRatingService getMovieService() {
			return new UserMovieRatingService();
		}
		
	}
	
	@Test
	public void getUserMovieRatingDetailsFoundTest() {
		
		UserMovieRating movieRating1 = new UserMovieRating(Integer.valueOf("1"), 1,1, 9.0, new Date());
		UserMovieRating movieRating2 = new UserMovieRating(Integer.valueOf("2"), 1,2, 8.0, new Date());
		UserMovieRating movieRating3 = new UserMovieRating(Integer.valueOf("3"), 1,3, 7.0, new Date());
		
		
		List<UserMovieRating> movieRatingList = new ArrayList<UserMovieRating>();
		movieRatingList.add(movieRating1);
		movieRatingList.add(movieRating2);
		movieRatingList.add(movieRating3);
		
		
		
		Optional<List<UserMovieRating>> userMovieRating = Optional.of(movieRatingList);
		
		Mockito.when(userMovieRatingRepository.findByUserId(1)).thenReturn(userMovieRating);
		
		List<MovieRatingBO> movieRatingBOList = userMovieRatingService.getUserMovieRatings(Integer.valueOf("1"));
		
		
		Assert.assertNotNull(movieRatingBOList);
		Assert.assertEquals(3, movieRatingBOList.size());
		
		
		
	}
	
	
	
	

}
