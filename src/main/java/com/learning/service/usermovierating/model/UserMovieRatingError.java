package com.learning.service.usermovierating.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMovieRatingError {
	
	private String code;
	private String message;
	
	

}
