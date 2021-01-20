package com.empinventory;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.empinventory.service.EmployeeService;
import com.empinventory.service.TaskService;

/**
 * Tests for uploading the file to database.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class FlatFlieUploadTest {

	private static String FILE_CONTENT;

	@BeforeAll
	public static void init() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Sachin 44").append(System.lineSeparator());
		sb.append("Ganguly 45").append(System.lineSeparator());
		sb.append("Dravid 47").append(System.lineSeparator());
		sb.append("Laxman 45").append(System.lineSeparator());
		sb.append("Sewagh 40").append(System.lineSeparator());
		sb.append("Kumble 48");
		FILE_CONTENT = sb.toString();
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;

	/**
	 * Test to verify if the file upload is successful and returns task for for reference.
	 * 
	 * @throws Exception Throw while executing the file upload
	 */
	@Test
	public void multipartFileUploadTest() throws Exception {
		final MockMultipartFile file = new MockMultipartFile("file", "test-data.txt", MediaType.APPLICATION_JSON_VALUE,
				FILE_CONTENT.getBytes());

		mockMvc.perform(multipart("/api/employee/upload").file(file).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("TaskID for your Reference => ")));
	}

	/**
	 * Test to verify the task status after completion of file processing.
	 * 
	 * @throws Exception Throw while executing the file upload
	 */
	@Test
	public void taskStatusTest() throws Exception {
		final MockMultipartFile file = new MockMultipartFile("file", "test-data.txt", MediaType.APPLICATION_JSON_VALUE,
				FILE_CONTENT.getBytes());

		final MvcResult result = mockMvc
				.perform(multipart("/api/employee/upload").file(file).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("TaskID for your Reference => ")))
				.andReturn();

		Thread.sleep(3000);

		final String content = result.getResponse().getContentAsString();
		final Integer taskId = Integer.valueOf(content.substring(29, content.length()));

		assertTrue(Objects.nonNull(taskId));

		mockMvc.perform(get("/api/employee/status/" + taskId)).andExpect(status().isOk())
				.andExpect(content().string("Sucessfully loaded to Database"));
	}
}
