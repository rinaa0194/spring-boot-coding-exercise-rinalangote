package com.telstra.codechallenge.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserMethodArgumentNotValidException extends RuntimeException {
	
	public UserMethodArgumentNotValidException(String message) {
		super(message);
	}

}
