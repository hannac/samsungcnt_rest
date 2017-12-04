package com.ryu.bigdata.vo;

public class Result {
	
	private String code;
	private String message;
	
	public String getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "{\"code\":\"" + this.code + "\", \"message\":\"" + this.message +"\"}";
	}

}