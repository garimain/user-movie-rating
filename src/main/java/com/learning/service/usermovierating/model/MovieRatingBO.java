package com.learning.service.usermovierating.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRatingBO {
	
	private Integer ratingId;
	private Integer movieId;
	private double rating;

}
