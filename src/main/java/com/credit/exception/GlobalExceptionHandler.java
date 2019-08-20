package com.credit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.credit.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BankException.class)
	public ResponseEntity<ResponseDto> ecomorseExceptionHandler(BankException ex, WebRequest request) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(ex.getMessage());
		responseDto.setStatusCode(401);

		return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> globalExceptionHandler(Exception exception, WebRequest request) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(exception.getMessage());
		responseDto.setStatusCode(401);
		return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);

	}

	
}