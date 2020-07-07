/**
 * 
 */
package com.vmware.interview.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Rabindra
 *
 */

@AllArgsConstructor
public class NumberGenerateResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1270248040669042715L;
	public UUID taskId;
		
}
