/**
 * 
 */
package com.vmware.interview.service;

import java.util.UUID;

import com.vmware.interview.dto.NumberData;
import com.vmware.interview.dto.NumberGenerateRequest;
import com.vmware.interview.dto.NumberGenerateResponse;
import com.vmware.interview.dto.NumberGenerateStatus;

/**
 * @author Rabindra
 *
 */
public interface INumberGeneratorService {
	
	/**
	 * @author Rabindra
	 * @description: responsible for generating the numbers as per the user defined goal and step values.
	 * @return: UUID as task id 
	 *
	 */
	NumberGenerateResponse generateNumber(NumberGenerateRequest numGenReq);
	
	/**
	 * @author Rabindra
	 * @description: responsible for checking and retrieving the number generation status
	 * @return: status description object which holds value as SUCCESS/IN_PROGRESS/ERROR
	 *
	 */
	NumberGenerateStatus getStatus(UUID uuid);
	
	/**
	 * @author Rabindra
	 * @description: responsible for retrieving the generated numbers
	 * @return: list of numbers in descending order
	 *
	 */
	NumberData getGeneratedNumbers(UUID uuid);
}
