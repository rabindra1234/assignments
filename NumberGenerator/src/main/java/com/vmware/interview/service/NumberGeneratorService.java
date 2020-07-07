/**
 * 
 */
package com.vmware.interview.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vmware.interview.constant.Status;
import com.vmware.interview.dto.NumberData;
import com.vmware.interview.dto.NumberGenerateRequest;
import com.vmware.interview.dto.NumberGenerateResponse;
import com.vmware.interview.dto.NumberGenerateStatus;
import com.vmware.interview.exception.DataNotFoundException;
import com.vmware.interview.exception.DataPersistException;
import com.vmware.interview.exception.InvalidRequestException;

/**
 * @author Rabindra
 *
 */
@Service
public class NumberGeneratorService implements INumberGeneratorService {
	
	ConcurrentHashMap<UUID, String> numGenContextHolder = new ConcurrentHashMap<>();

	@Override
	public NumberGenerateResponse generateNumber(NumberGenerateRequest numGenReq) {
		validateRequestParams(numGenReq);
		UUID taskId = UUID.randomUUID();
		CompletableFuture
		.supplyAsync(()-> {
			return createNumber(taskId, numGenReq);
		})
		.thenApply(numbers -> {
			return persistData(taskId, numbers);
		})
		.handle((status, exception) -> {
			if (exception != null) {
				//logger stmt will goes here
				exception.printStackTrace();
				return Status.ERROR;
			} else {
				return status;
			}
		})
		.thenAccept(status -> {
			updateTaskStatus(taskId, status);
		});

		return new NumberGenerateResponse(taskId);
	}
	
	/**
	 * @author Rabindra
	 * @description: responsible for goal and step value validation.
	 * @param numGenReq
	 *
	 */
	private void validateRequestParams(NumberGenerateRequest numGenReq) {
		try {
			if(!ObjectUtils.isEmpty(numGenReq)) {
				int goal = Integer.parseInt(numGenReq.getGoal()); 
				int step = Integer.parseInt(numGenReq.getStep()); 
			}
		} catch (NumberFormatException exp) {
			throw new InvalidRequestException("Invalid Goal / Step value provided");
		}

	}
	
	/**
	 * @author Rabindra
	 * @description: responsible for numbers create based on the goal and step.
	 * @param taskId
	 * @param numGenReq
	 * @return numbers
	 *
	 */
	private List<Integer> createNumber(UUID taskId, NumberGenerateRequest numGenReq) {
		numGenContextHolder.put(taskId, Status.IN_PROGRESS.toString());
		int goal = Integer.parseInt(numGenReq.getGoal()); 
		int step = Integer.parseInt(numGenReq.getStep()); 
		int rangeStart = 0; 
		int rangeEnd = (goal - rangeStart) / step;
		List<Integer> numbers = IntStream.rangeClosed(rangeStart, rangeEnd) 
				.map(number -> rangeStart + (rangeEnd - number))
				.mapToObj(reversedNumber ->(rangeStart + reversedNumber * step))
				.collect(Collectors.toList()); 
		return numbers;
	}
	
	/**
	 * @author Rabindra
	 * @description: responsible for save numbers into a file.
	 * @param taskId
	 * @param numbers
	 * @return status
	 *
	 */
	private Status persistData(UUID taskId, List<Integer> numbers) 
			throws DataPersistException {
		try {
			createTempDataDir();
			Files.write(Paths.get(filePathBuilder(taskId.toString())), 
					numbers.stream().map(number -> String.valueOf(number)).collect(Collectors.toList()), 
					StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, 
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new DataPersistException("Exception Occurred during data persist", e);
		}
		return Status.SUCCESS;
	}
	
	/**
	 * @author Rabindra
	 * @description: responsible for update the task success / failure status.
	 * @param taskId
	 * @param status
	 *
	 */
	private void updateTaskStatus(UUID taskId, Status status) {
		if(numGenContextHolder.containsKey(taskId)) {
			numGenContextHolder.put(taskId, status.name());
		}
	}
	
	
	
	@Override
	public NumberGenerateStatus getStatus(UUID taskId) {
		NumberGenerateStatus numGenStatus = new NumberGenerateStatus();
		if(numGenContextHolder.containsKey(taskId)) {
			numGenStatus.setStatus(numGenContextHolder.get(taskId));
		} else {
			throw new DataNotFoundException("Invalid Task Id Provided");
		}
		return numGenStatus;
	}

	@Override
	public NumberData getGeneratedNumbers(UUID taskId) {
		NumberData numberData = new NumberData();
		List<Integer> generatedNumbers = null;
		if(numGenContextHolder.containsKey(taskId)) {
			String status = numGenContextHolder.get(taskId);
			if(Status.SUCCESS.toString().equals(status)) {
				String filePath = filePathBuilder(taskId.toString());
				File file = new File(filePath);
				if(file.exists()) {
					try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
						generatedNumbers = stream.map(num -> Integer.parseInt(num)).collect(Collectors.toList());
						numberData.setResult(generatedNumbers);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					throw new DataNotFoundException("Invalid Task Id Provided");
				}
			}
		} else {
			throw new DataNotFoundException("No Data Found");
		}
		return numberData;
	}
	
	/**
	 * @author Rabindra
	 * @description: responsible for build complete file path.
	 * @return filePath
	 *
	 */
	private String filePathBuilder(String taskId) {
		return new StringBuilder()
				.append(System.getProperty("user.dir"))
				.append("\\temp\\")
				.append(taskId)
				.append("_output.txt")
				.toString();
	}

	/**
	 * @author Rabindra
	 * @description: responsible for create a temporary file.
	 * @return true / false
	 *
	 */
	private boolean createTempDataDir() {
		boolean isDirCreated = false;
		String userTempDirectory = new StringBuilder()
				.append(System.getProperty("user.dir"))
				.append("\\temp\\").toString();
		File file = new File(userTempDirectory);
		if(!file.exists()) {
			isDirCreated = file.mkdir();
		}
		return isDirCreated;
	}

}
