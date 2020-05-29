package com.allianz.assignement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.allianz.assignement.db.EmployeeDAO;
import com.allianz.assignement.entity.Employee;
import com.allianz.assignement.repo.EmployeeRepository;

@Service
public class EmployeesInfoService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeDAO employeeDAO;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Optional<List<Employee>> getEmployeesList() {
		return Optional.of(employeeRepository.findAll());
	}
	
	public Optional<Employee> getEmployeeByID(int id) {
		return employeeRepository.findById(id);
	}
	
	public Optional<Employee> insertEmployee(Employee employee) {
		int empID = employeeDAO.getEmployeeId();
		employee.setId(empID);
		return Optional.of(employeeRepository.save(employee));
	}
	
	public int updateEmployee(Employee employee) {
		return employeeDAO.updateEmployee(employee);
	}

	//WE can also delete by using JPA but to show proper result using JDBCTemplate
	public int deleteEmployee(int id) {
		return employeeDAO.deleteEmployee(id);
	}
	
}
