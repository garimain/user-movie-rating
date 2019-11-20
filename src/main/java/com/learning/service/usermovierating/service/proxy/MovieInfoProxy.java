package com.learning.service.usermovierating.service.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.learning.service.usermovierating.model.MovieInfoSearchResponseBO;


@Component
public class MovieInfoProxy {
	
	Logger logger = LoggerFactory.getLogger(MovieInfoProxy.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Value("${user_movie_rating.movie_info_service.url}")
	private String movieInfoServiceUrl;
	
	@HystrixCommand(fallbackMethod = "fallbackMovieDetails")
	public MovieInfoSearchResponseBO getMovieDetails(String movieIds) {
		
		logger.info("getMovieDetails - is called with inputs {} ", movieIds);
		
		
		MovieInfoSearchResponseBO movieInfoSearchResponse = null;
		
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(movieInfoServiceUrl);
			builder.queryParam("movieIds", movieIds);
			
			logger.info("getMovieDetails - the url that is invoked is {} ", movieInfoServiceUrl);
			ResponseEntity<MovieInfoSearchResponseBO> movieResponse = restTemplate.getForEntity(builder.toUriString(), MovieInfoSearchResponseBO.class);
			
			if (movieResponse.getStatusCode()== HttpStatus.OK) {
				logger.info("getMovieDetails - Got the movie details successfully for inputs {} ", movieIds);
				movieInfoSearchResponse = movieResponse.getBody();
			} else {
				//TODO: Ideally this should not be null but some exception to indicate caller than service call has given error
				return null;
			}
		
		} catch (Exception ex) {
			
			logger.error("getMovieDetails - Exception occured while calling user movie service {} ", ex.getMessage());
			throw ex;
			
		}
		
		return movieInfoSearchResponse;
		
	}
	
	private MovieInfoSearchResponseBO fallbackMovieDetails(String movieIds) {
		
		return null;
		
		
	}
	
	
	
	
}
