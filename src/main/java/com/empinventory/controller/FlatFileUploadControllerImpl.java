package com.empinventory.controller;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empinventory.service.EmployeeService;
import com.empinventory.service.TaskService;

/**
 * Rest Controller for file upload operations.
 */
@RestController
@RequestMapping("/api/employee")
public class FlatFileUploadControllerImpl implements FlatFileUploadController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TaskService taskService;

	/**
	 * Asynchronously reads the uploaded file and write the data to Database.
	 * 
	 * @param file The File that contains data.
	 * @return the TaskID for future use to query the status of the progress.  
	 */
	@PostMapping("upload")
	@Override
	public ResponseEntity<String> uploadFile(@RequestParam("file") final MultipartFile file) {
		int taskID;
		try {
			taskID = employeeService.uploadFile(file.getInputStream());
		} catch (IOException | InterruptedException e) {
			return new ResponseEntity<String>("Failed" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("TaskID for your Reference => " + taskID, HttpStatus.OK);
	}

	/**
	 * Returns the status for the given task id.
	 * 
	 * @param taskId The Task ID.
	 * @return The status message.
	 */
	@GetMapping("status/{taskId}")
	@Override
	public ResponseEntity<String> getStatus(@PathVariable final int taskId) {
		final String status = taskService.getStatus(taskId);
		if (Objects.nonNull(status)) {
			return new ResponseEntity<String>(status, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Invalid Task ID", HttpStatus.OK);
	}
}
