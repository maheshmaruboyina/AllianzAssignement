package com.allianz.assignement.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.allianz.assignement.entity.Employee;

@Service
public class EmployeeDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int updateEmployee(Employee employee) {
		return jdbcTemplate.update(
				getUpdateQuery(),
                new Object[]{employee.getEmployeeName(), employee.getEmployeeAge(), employee.getSalary(), employee.getId()});
	}

	//WE can also delete by using JPA but to show proper result using JDBCTemplate
	public int deleteEmployee(int id) {
		return jdbcTemplate.update(
				deleteQuery(),
                new Object[]{id});
	}
	
	public int getEmployeeId() {
		return jdbcTemplate.queryForObject(getEmpSeqId(), new Object[] {}, Integer.class);
	}
	
	private String getUpdateQuery() {
		return "update employee set EMPLOYEE_NAME = ?, EMPLOYEE_AGE = ?, EMPLOYEE_SALARY = ? where id = ?";
	}
	
	private String deleteQuery() {
		return "delete from employee where id=?";
	}
	
	private String getEmpSeqId() {
		return "SELECT LPAD(employee_seq.NEXTVAL, 10,'0') FROM DUAL";
	}

}
