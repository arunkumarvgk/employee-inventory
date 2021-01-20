package com.empinventory;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.empinventory.model.Employee;
import com.empinventory.repository.EmployeeRepository;

/**
 * Tests for custom repository methods findByName and FindByAge
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeTest {

	@Autowired
	private EmployeeRepository empRepository;

	@Test
	public void ShouldFindByAge() {
		empRepository.save(new Employee("Dravid", 43));
		empRepository.save(new Employee("Dravid 2", 42));
		empRepository.save(new Employee("Dravid 3", 42));
		empRepository.save(new Employee("Dravid 4", 43));

		final List<Employee> employees = empRepository.findByAge(43);
		assertEquals(2, employees.size());
		assertEquals("Dravid", employees.get(0).getName());
		assertEquals("Dravid 4", employees.get(1).getName());
	}
	
	@Test
	public void ShouldFindByName() {
		empRepository.save(new Employee("Dravid", 43));

		final List<Employee> employees = empRepository.findByName("Dravid");
		assertEquals(1, employees.size());
		assertEquals("Dravid", employees.get(0).getName());
	}
}
