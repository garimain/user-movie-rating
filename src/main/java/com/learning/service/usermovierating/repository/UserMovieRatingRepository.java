package com.learning.service.usermovierating.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.service.usermovierating.repository.model.UserMovieRating;

@Repository
public interface UserMovieRatingRepository extends JpaRepository <UserMovieRating, Integer> {
	
	public Optional<List<UserMovieRating>> findByUserId(Integer userId);
	public Optional<List<UserMovieRating>> findByUserIdAndMovieId(Integer userId, Integer movieId);
	
	

}
