package com.ryu.bigdata.exception;

public class RestApiException extends Exception {

	private String message;

	public RestApiException() {
		super();
	}

	public RestApiException(String message) {
		super(message);
	}
	
	

}
