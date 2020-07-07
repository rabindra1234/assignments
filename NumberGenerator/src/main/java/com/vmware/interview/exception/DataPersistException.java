/**
 * 
 */
package com.vmware.interview.exception;

/**
 * @author Rabindra
 *
 */
public class DataPersistException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8490720073339521662L;
	public DataPersistException() {
        super();
    }
    public DataPersistException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataPersistException(String message) {
        super(message);
    }
    public DataPersistException(Throwable cause) {
        super(cause);
    }
}