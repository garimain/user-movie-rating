package com.learning.service.usermovierating.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfoSearchResponseBO {
	
	private List<MovieBO> movieList;
	
	//Other parameters can follow here e.g. pagination, warnings etc.
	
	
	
	
}
