/**
 * 
 */
package com.vmware.interview.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.interview.constant.Status;
import com.vmware.interview.controller.NumberGeneratorController;
import com.vmware.interview.dto.NumberData;
import com.vmware.interview.dto.NumberGenerateRequest;
import com.vmware.interview.dto.NumberGenerateResponse;
import com.vmware.interview.dto.NumberGenerateStatus;
import com.vmware.interview.service.NumberGeneratorService;

/**
 * @author Rabindra
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class NumberGeneratorControllerTests {
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	NumberGeneratorService numberGeneratorServiceMock;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private NumberGeneratorController numGenController;
	
	NumberGenerateStatus numGenStatus;
	
	NumberData numberData;
	
	private static final String VERSION = "version";
	private static final String VERSION_NUM = "1.0";
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		numberGeneratorServiceMock = org.mockito.Mockito.mock(NumberGeneratorService.class);
		numGenStatus = new NumberGenerateStatus();
		numGenStatus.setStatus(Status.IN_PROGRESS.toString());
		numberData = new NumberData();
		numberData.setResult(generateDummyNumbers());
		
	}
	
	private List<Integer> generateDummyNumbers() {
		List<Integer> list=new ArrayList<>();
		list.add(10);list.add(8);list.add(6);
		list.add(4);list.add(2);list.add(0);
		return list;
	}
	
	private NumberGenerateRequest prepareRequest(){
		NumberGenerateRequest numGenReq = new NumberGenerateRequest();
		numGenReq.setGoal("10");
		numGenReq.setStep("2");
		return  numGenReq;
	}
	
	@Test
	@DisplayName("Test Method for Number Generate")
	public void generateNumberTest() {
		try {
			UUID taskId = UUID.fromString("045fbe62-d354-4902-bbf6-ba1ec0db6700");
			NumberGenerateRequest numGenReq = prepareRequest();
			String reqJson;
			reqJson = objectMapper.writeValueAsString(numGenReq);

			NumberGenerateResponse response = new NumberGenerateResponse(taskId);

			Mockito.when(numberGeneratorServiceMock.generateNumber(numGenReq)).thenReturn(response);

			mockMvc.perform(post("/api/generate")
					.param(VERSION, VERSION_NUM)
					.content(reqJson)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();
		} catch (Exception e) {
			Assert.fail("Test Method for Number Generate Failed");
		}
	}
	
	@Test
	@DisplayName("Test Method for Task status check")
	public void getStatusTest() {
		try {
			Mockito.when(numberGeneratorServiceMock.getStatus(UUID.fromString("045fbe62-d354-4902-bbf6-ba1ec0db6700")))
			.thenReturn(numGenStatus);

			mockMvc.perform(get("/api/tasks/045fbe62-d354-4902-bbf6-ba1ec0db6700/status")
					.param(VERSION, VERSION_NUM))
			.andExpect(status().isOk());
		} catch (Exception e) {
			Assert.fail("Test Method for Task status check Failed");
		}

	}
	
	@Test
	@DisplayName("Test Method to Retrieve the Numbers")
	public void getNumbersTest() {
		try {
			Mockito.when(numberGeneratorServiceMock.getGeneratedNumbers(UUID.fromString("045fbe62-d354-4902-bbf6-ba1ec0db6700")))
			.thenReturn(numberData);

			mockMvc.perform(get("/api/tasks/045fbe62-d354-4902-bbf6-ba1ec0db6700")
					.param("action", "get_numlist")
					.param(VERSION, VERSION_NUM))
			.andExpect(status().isOk());

		} catch (Exception e) {
			Assert.fail("Test Method to Retrieve the Numbers Failed");
		}
	}
}
