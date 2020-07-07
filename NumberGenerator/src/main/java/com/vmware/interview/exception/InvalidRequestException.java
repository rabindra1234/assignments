/**
 * 
 */
package com.vmware.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Rabindra
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8490720073339521662L;
	public InvalidRequestException() {
        super();
    }
    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidRequestException(String message) {
        super(message);
    }
    public InvalidRequestException(Throwable cause) {
        super(cause);
    }
}