/**
 * 
 */
package com.vmware.interview.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Rabindra
 *
 */
@Getter
@Setter
public class NumberData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2946374861368863804L;
	public List<Integer> result;

}
