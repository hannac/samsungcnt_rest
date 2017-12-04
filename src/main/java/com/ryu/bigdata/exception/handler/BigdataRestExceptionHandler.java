package com.ryu.bigdata.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.amazonaws.http.exception.HttpRequestTimeoutException;
import com.ryu.bigdata.controller.DbTestController;
import com.ryu.bigdata.exception.NoContentException;
import com.ryu.bigdata.vo.Result;

/**
 * 여러 HTTP 응답 상태에 대한 예외 핸들러 설정
 * @author azc2004
 *
 */
@RestControllerAdvice
public class BigdataRestExceptionHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(BigdataRestExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {NumberFormatException.class, NullPointerException.class})
	public JSONObject handleBadRequest(Throwable ex) {
		ResponseEntity<?> resEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		ex.printStackTrace();
		
		return makeErrorResult(resEntity, null, ex);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = {NoHandlerFoundException.class})
	public JSONObject handleNotFound(Throwable ex) {
		ResponseEntity<?> resEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return makeErrorResult(resEntity, null, ex);		
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {Exception.class})
	public JSONObject handleInternalServerError(Throwable ex) {
		ResponseEntity<?> resEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		return makeErrorResult(resEntity, null, ex);		
	}
	
	@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
	@ExceptionHandler(value = {HttpRequestTimeoutException.class})
	public JSONObject handleRequestTimeout(Throwable ex) {
		ResponseEntity<?> resEntity = new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
		
		return makeErrorResult(resEntity, "요청시간 초과", ex);		
	}
	
	@ResponseStatus
	@ExceptionHandler(value = {NoContentException.class})
	public JSONObject handleNoContent(Throwable ex) {
		ResponseEntity<?> resEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return makeErrorResult(resEntity, "컨텐츠 없음", ex);
	}
	
	/**
	 * 에러 응답용 result 객체를 생성
	 * 
	 * @param resEntity
	 * @param message HttpStatus 의 상태 메시지. null 로 입력되면 HTTP 상태 코드에 따른 기본 영문 상태 메시지를 사용한다.
	 * @return
	 */
	private JSONObject makeErrorResult(ResponseEntity<?> resEntity, String message, Throwable ex) {		
		Map<String, Object> jsonMap = new HashMap<>();
		
		Result result = new Result();
		
		result.setCode(resEntity.getStatusCode().toString());
		
		if (message == null) {
			result.setMessage(resEntity.getStatusCode().getReasonPhrase());
		} else {
			result.setMessage(message);
		}
		
		// 예외 객체 스택 출력
//		ex.printStackTrace();
		
		jsonMap.put("result", result);
		
		JSONObject jsonObject = new JSONObject(jsonMap);		
		
		return jsonObject;
	}
}
