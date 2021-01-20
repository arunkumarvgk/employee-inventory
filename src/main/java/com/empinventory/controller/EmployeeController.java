package com.empinventory.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.empinventory.model.Employee;

@RestController
public interface EmployeeController {
	ResponseEntity<String> create(final Employee employee);

	void update(final Employee employee);

	void delete(Integer id);

	ResponseEntity<?> find(Integer id);

	ResponseEntity<?> findByName(String name);
	
	ResponseEntity<List<Employee>> findAll();
}
