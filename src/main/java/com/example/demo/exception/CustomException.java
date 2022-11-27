package com.example.demo.exception;

public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CustomException() {
		
	}
	
	public CustomException(Exception e) {
		super(e);
	}
	
	public CustomException(String message, Exception e) {
		super(message, e);
	}
	
	

}
