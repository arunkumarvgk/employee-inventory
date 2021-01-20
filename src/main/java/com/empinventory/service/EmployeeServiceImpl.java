package com.empinventory.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.empinventory.model.Employee;
import com.empinventory.repository.EmployeeRepository;
import com.sun.istack.Nullable;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TaskService taskService;

	@Value("${file.upload.delay}")
	private int delay;
	
	/* Log the status message after inserting the given number of records */
	@Value("${file.upload.logAfterRecordsCount}")
	private int logAfterRecordsCount;

	/**
	 * Loads the data from file to to database and also updates the task accordingly.
	 * 
	 * @param inputStream The uploaded file input stream.
	 * @param taskId The task id created for this process.
	 */
	@Override
	public void loadData(@NonNull final InputStream inputStream, @NonNull final int taskId) throws IOException, InterruptedException {
		int count = 0;
		
		/* Update the task before starting */
		taskService.updateTask(taskId, "Loading to Database");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = reader.readLine();

		while (( line = reader.readLine()) != null) {
			/*Little Delay for testing*/
			Thread.sleep(delay);
			final String values[] = line.split(" ");
			employeeRepository.save(new Employee(values[0], Integer.valueOf(values[1])));
			count++;
			
			/* Update the task status for every 100 records (can be changed)*/
			if ((count % logAfterRecordsCount) == 0) {
				taskService.updateTask(taskId, "Processed " + count + " Record(s)");
			}
		}
		
		/* Update the task After completion */
		taskService.updateTask(taskId, "Sucessfully loaded to Database");
		reader.close();
	}

	@Override
	public int create(@NonNull final String name, @NonNull final int age) {
		return employeeRepository.save(new Employee(name, age)).getId();
	}

	@Override
	public void update(@NonNull final int id, @Nullable final String name, @Nullable final int age) {
		final Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		
		if (optionalEmployee.isPresent()) {
			final Employee emp = optionalEmployee.get();
			
			if (Objects.nonNull(name)) {
				emp.setName(name);
			}
			
			if (Objects.nonNull(age)) {
				emp.setAge(age);
			}
			
			employeeRepository.save(emp);
		}
	}

	@Override
	public void delete(@NonNull final int id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee find(@NonNull final int id) {
		final Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			return optionalEmployee.get();
		}
		return null;
	}

	@Override
	public List<Employee> findByName(@NonNull final String name) {
		return employeeRepository.findByName(name);
	}

	@Override
	public List<Employee> findByAge(@NonNull final int age) {
		return employeeRepository.findByAge(age);
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}
}
