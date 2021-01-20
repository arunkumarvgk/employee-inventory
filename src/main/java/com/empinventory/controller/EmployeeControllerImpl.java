package com.empinventory.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empinventory.model.Employee;
import com.empinventory.service.EmployeeService;

/**
 * Rest Controller for {@link Employee} requests.
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeControllerImpl implements EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Override
	@PostMapping
	public ResponseEntity<String> create(@RequestBody final Employee employee) {
		if (Objects.isNull(employee.getName()) || Objects.isNull(employee.getAge())) {
			return new ResponseEntity<String>("Values cannot be empty", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(String.valueOf(employeeService.create(employee.getName(), employee.getAge())), HttpStatus.OK);
	}

	@Override
	@PutMapping
	public void update(@RequestBody final Employee employee) {
		employeeService.update(employee.getId(), employee.getName(), employee.getAge());
	}

	@Override
	@DeleteMapping("{id}")
	public void delete(@PathVariable final Integer id) {
		employeeService.delete(id);
	}

	@Override
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable final Integer id) {
		if (Objects.isNull(id)) {
			return new ResponseEntity<String>("Id cannot be empty", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Employee>(employeeService.find(id.intValue()), HttpStatus.OK);
	}

	@Override
	@GetMapping
	public ResponseEntity<?> findByName(@RequestParam("name") final String name) {
		if (Objects.isNull(name)) {
			return new ResponseEntity<String>("Name cannot be empty", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Employee>>(employeeService.findByName(name), HttpStatus.OK);
	}


	@Override
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> findAll() {
		return new ResponseEntity<List<Employee>>(employeeService.findAll(), HttpStatus.OK);
	}
}
