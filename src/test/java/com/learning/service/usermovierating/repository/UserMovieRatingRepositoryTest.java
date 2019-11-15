package com.learning.service.usermovierating.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.service.usermovierating.repository.model.UserMovieRating;




@RunWith(SpringRunner.class)
@DataJpaTest
public class UserMovieRatingRepositoryTest {

	@Autowired
	private TestEntityManager tem;
	
	@Autowired
	private UserMovieRatingRepository repository;
	
	@Test
	public void addUserMovieRating() {
		UserMovieRating userMovieRating = new UserMovieRating (null, 1, 1, 9, new Date());
		
		UserMovieRating persistentMovie = tem.persistFlushFind(userMovieRating);
		
		Assert.assertTrue(persistentMovie.getRatingId() > 0);
		Assert.assertEquals(persistentMovie.getMovieId(), userMovieRating.getMovieId());
		
	}
	
	@Test
	public void findByUserId() {
		UserMovieRating persistentMovie1 = repository.save(new UserMovieRating (null, 1, 1, 9, new Date()));
		UserMovieRating persistentMovie2 = repository.save(new UserMovieRating (null, 1, 2, 7, new Date()));
		
		Optional<List<UserMovieRating>> searchedMovieRatings = repository.findByUserId(1);
		
		Assert.assertNotNull(searchedMovieRatings.get());
		Assert.assertEquals(2, searchedMovieRatings.get().size());
		
		
	}
	
	
	
	
}
