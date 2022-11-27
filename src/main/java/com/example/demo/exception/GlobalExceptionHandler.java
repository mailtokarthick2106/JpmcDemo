package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> handleException(CustomException e) {
		
		return new ResponseEntity<>("Exception calling Datasource api", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
