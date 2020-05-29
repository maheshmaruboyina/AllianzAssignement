package com.allianz.assignement.model;

import java.util.List;

import com.allianz.assignement.entity.Employee;

public class GetEmployeesListResponse extends EmployeeCommonResponse{
	private List<Employee> employeelist;
	
	public List<Employee> getEmployeelist() {
		return employeelist;
	}
	public void setEmployeelist(List<Employee> employeelist) {
		this.employeelist = employeelist;
	}
}
