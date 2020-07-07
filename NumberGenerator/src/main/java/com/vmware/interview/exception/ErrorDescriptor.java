/**
 * 
 */
package com.vmware.interview.exception;

import lombok.Builder;

/**
 * @author Rabindra
 * @description responsible for error details holder
 *
 */
@Builder
public class ErrorDescriptor {
	
	public int errorCode;
	public String errorMessage;

}
