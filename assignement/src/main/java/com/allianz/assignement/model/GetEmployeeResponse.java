package com.allianz.assignement.model;

import com.allianz.assignement.entity.Employee;

public class GetEmployeeResponse extends EmployeeCommonResponse{
	private Employee employee;
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
