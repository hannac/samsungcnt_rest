package com.ryu.bigdata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "컨텐츠 없음")
public class NoContentException extends RestApiException {

	public NoContentException() {
		super("컨텐츠 없음");
	}
	
	public NoContentException(String message) {
		super(message);
	}
}
