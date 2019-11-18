package com.learning.service.usermovierating.model;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserMovieRatingSearchBO {
	
	private String userId;
	private Optional<String> movieId;
	
}
