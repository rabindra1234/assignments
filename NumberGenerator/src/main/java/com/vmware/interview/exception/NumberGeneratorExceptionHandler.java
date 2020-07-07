/**
 * 
 */
package com.vmware.interview.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Rabindra
 *
 */
@ControllerAdvice
public class NumberGeneratorExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { InvalidRequestException.class })
	public ResponseEntity<ErrorDescriptor> handleInvalidRequest(Exception ex) {
		//logger stmt will goes here
		ex.printStackTrace();
		ErrorDescriptor descriptor = ErrorDescriptor
				.builder()
				.errorCode(HttpStatus.BAD_REQUEST.value())
				.errorMessage(ex.getMessage())
				.build();
		return new ResponseEntity<ErrorDescriptor>(descriptor, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { DataNotFoundException.class })
	public ResponseEntity<ErrorDescriptor> handleNoDataFound(Exception ex) {
		//logger stmt will goes here
		ex.printStackTrace();
		ErrorDescriptor descriptor = ErrorDescriptor
				.builder()
				.errorCode(HttpStatus.NOT_FOUND.value())
				.errorMessage(ex.getMessage())
				.build();
		return new ResponseEntity<ErrorDescriptor>(descriptor, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { IOException.class })
	public ResponseEntity<ErrorDescriptor> handleIOException(Exception ex) {
		//logger stmt will goes here
		ex.printStackTrace();
		ErrorDescriptor descriptor = ErrorDescriptor
				.builder()
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.build();
		return new ResponseEntity<ErrorDescriptor>(descriptor, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorDescriptor> handleException(Exception ex) {
		//logger stmt will goes here
		ex.printStackTrace();
		ErrorDescriptor descriptor = ErrorDescriptor
				.builder()
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.build();
		return new ResponseEntity<ErrorDescriptor>(descriptor, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
