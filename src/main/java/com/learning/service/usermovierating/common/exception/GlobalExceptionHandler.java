package com.learning.service.usermovierating.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.learning.service.usermovierating.model.UserMovieRatingError;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserMovieRatingNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public UserMovieRatingError handleNotFoundException(Exception ex, WebRequest request) {
		
		UserMovieRatingError error;
		if (ex instanceof UserMovieRatingNotFoundException) {
			error = new UserMovieRatingError("UserMovieRatingNotFound", ex.getMessage());
			
		} else {
			error = new UserMovieRatingError("UnknownError", ex.getMessage());
		}
		
		return error;
		
	}
	
	
	@ExceptionHandler(UserMovieRatingNotAddedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserMovieRatingError handleNotAddedException(Exception ex, WebRequest request) {
		
		UserMovieRatingError error;
		if (ex instanceof UserMovieRatingNotAddedException) {
			error = new UserMovieRatingError("UserMovieRatingNotAdded", ex.getMessage());
			
		} else {
			error = new UserMovieRatingError("UnknownError", ex.getMessage());
		}
		
		return error;
		
	}
	
	
	@ExceptionHandler(UserMovieRatingNotUpdatedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserMovieRatingError handleNotUpdatedException(Exception ex, WebRequest request) {
		
		UserMovieRatingError error;
		if (ex instanceof UserMovieRatingNotUpdatedException) {
			error = new UserMovieRatingError("UserMovieRatingNotUpdated", ex.getMessage());
			
		} else {
			error = new UserMovieRatingError("UnknownError", ex.getMessage());
		}
		
		return error;
		
	}
	
	
}
