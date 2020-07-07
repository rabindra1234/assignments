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
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8490720073339521662L;
	public DataNotFoundException() {
        super();
    }
    public DataNotFoundException(String message) {
        super(message);
    }
}