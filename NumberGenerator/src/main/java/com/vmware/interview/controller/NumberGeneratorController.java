/**
 * 
 */
package com.vmware.interview.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.interview.dto.NumberData;
import com.vmware.interview.dto.NumberGenerateRequest;
import com.vmware.interview.dto.NumberGenerateResponse;
import com.vmware.interview.dto.NumberGenerateStatus;
import com.vmware.interview.service.INumberGeneratorService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Rabindra
 *
 */
@RestController
@RequestMapping("/api")
public class NumberGeneratorController {

	private static final String API_VERSION = "version=1.0";
	private static final String GET_API_ACTION = "action=get_numlist";
	
	@Autowired
	private INumberGeneratorService numberGeneratorService;

	@ApiOperation("Endpoint Operation is responsible for Generate Number")
	@PostMapping(value = "/generate", params = API_VERSION, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NumberGenerateResponse> generateNumber(@RequestBody NumberGenerateRequest numGenReq) {
		return ResponseEntity.accepted().body(numberGeneratorService.generateNumber(numGenReq));
	}

	@ApiOperation("Endpoint Operation is responsible to check status of the task")
	@GetMapping(value = "/tasks/{taskId}/status", params = API_VERSION)
	public ResponseEntity<NumberGenerateStatus> getStatus(@PathVariable("taskId") String taskId) {
		return ResponseEntity.ok().body(numberGeneratorService.getStatus(UUID.fromString(taskId)));
	}
	
	@ApiOperation("Endpoint Operation is responsible for Retriving the Generated Numbers")
	@GetMapping(value = "/tasks/{taskId}", params = {GET_API_ACTION, API_VERSION})
	public ResponseEntity<NumberData> getNumbers(@PathVariable("taskId") String taskId) {
		return ResponseEntity.ok().body(numberGeneratorService.getGeneratedNumbers(UUID.fromString(taskId)));
	}


}
