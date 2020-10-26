package com.wander.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wander.dto.ResponseDTO;

@ControllerAdvice
public class WanderExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoActiveUserFoundException.class)
	public final ResponseEntity<ResponseDTO> noActiveUserFoundException(NoActiveUserFoundException ex) {
		return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ResponseDTO> handleException(Exception ex) {
		return new ResponseEntity<>(new ResponseDTO(ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
