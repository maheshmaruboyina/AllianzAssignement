package com.allianz.assignement.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.allianz.assignement.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findById(int id);
	
}