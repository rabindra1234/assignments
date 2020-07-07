/**
 * 
 */
package com.vmware.interview.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Rabindra
 *
 */
@Getter
@Setter
public class NumberGenerateRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5979979931843043823L;
	public String goal;
	public String step;

}
