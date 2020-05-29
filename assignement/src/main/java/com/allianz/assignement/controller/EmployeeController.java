package com.allianz.assignement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.assignement.config.ApplicationConstants;
import com.allianz.assignement.entity.Employee;
import com.allianz.assignement.model.DeleteEmployeeRequest;
import com.allianz.assignement.model.EmployeeResponse;
import com.allianz.assignement.model.GetEmployeeResponse;
import com.allianz.assignement.model.GetEmployeesListResponse;
import com.allianz.assignement.service.EmployeesInfoService;

@RestController
@RequestMapping(value = "/employee", produces = { ApplicationConstants.response_type_json })
public class EmployeeController {
	 Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	 @Autowired
	 EmployeesInfoService employeesInfoService;
	 
	 @GetMapping(path = "/employeesList")
	 @ResponseStatus(HttpStatus.OK)
	 public GetEmployeesListResponse getEmployeesList() {
		 logger.info("getEmployeesList Started");
		 GetEmployeesListResponse getEmployeesListResponse = new GetEmployeesListResponse();
		 getEmployeesListResponse.setEmployeelist(employeesInfoService.getEmployeesList().orElse(null));
		 if(employeesInfoService.getEmployeesList().isPresent()) {
			 getEmployeesListResponse.setErrorCode(ApplicationConstants.SUCCESS_CODE);
			 getEmployeesListResponse.setMessage(ApplicationConstants.GET_EMPLOYEE_LIST_SUCCESS);
		 }else {
			 getEmployeesListResponse.setErrorCode(ApplicationConstants.FAILURE_CODE);
			 getEmployeesListResponse.setMessage(ApplicationConstants.GET_EMPLOYEE_LIST_FAILED);
		 }
		 logger.info("getEmployeesList End");
		 return getEmployeesListResponse;
	 }
	 
	 @GetMapping(path = "/employeeInfo/{employeeID}")
	 @ResponseStatus(HttpStatus.OK)
	 public GetEmployeeResponse getEmployee(@PathVariable int employeeID) {
		 logger.info("getEmployee Started");
		 GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse();
		 getEmployeeResponse.setEmployee(employeesInfoService.getEmployeeByID(employeeID).orElse(null));
		 if(employeesInfoService.getEmployeeByID(employeeID).isPresent()) {
			 getEmployeeResponse.setErrorCode(ApplicationConstants.SUCCESS_CODE);
			 getEmployeeResponse.setMessage(ApplicationConstants.GET_EMPLOYEE_SUCCESS);
		 }else {
			 getEmployeeResponse.setErrorCode(ApplicationConstants.FAILURE_CODE);
			 getEmployeeResponse.setMessage(ApplicationConstants.GET_EMPLOYEE_FAILED);
		 }
		 logger.info("getEmployee End");
		 return getEmployeeResponse;
	 }
	 
	 @PostMapping(path = "/addEmployee")
	 @ResponseStatus(HttpStatus.OK) 
	 public EmployeeResponse addEmployee(@RequestBody Employee employee) {
		 logger.info("addEmployee Started");
		 EmployeeResponse employeeResponse = new EmployeeResponse();
		 if(employeesInfoService.insertEmployee(employee).isPresent()) {
			 employeeResponse.setErrorCode(ApplicationConstants.SUCCESS_CODE);
			 employeeResponse.setMessage(employee.getEmployeeName().concat(ApplicationConstants.EMPLOYEE_SAVE_SUCCESS));
		 }else {
			 employeeResponse.setErrorCode(ApplicationConstants.FAILURE_CODE);
			 employeeResponse.setMessage(employee.getEmployeeName().concat(ApplicationConstants.EMPLOYEE_SAVE_FAILED));
		 }
		 logger.info("addEmployee end");
		 return employeeResponse;
	 }
	 
	 @PostMapping(path = "/updateEmployee")
	 @ResponseStatus(HttpStatus.OK)
	 public EmployeeResponse updateEmployee(@RequestBody Employee employee) {
		 logger.info("updateEmployee Started");
		 EmployeeResponse employeeResponse = new EmployeeResponse();
		 int numberofRecords = employeesInfoService.updateEmployee(employee);
		 if(numberofRecords!=0) {
			 employeeResponse.setErrorCode(ApplicationConstants.SUCCESS_CODE);
			 employeeResponse.setMessage(employee.getEmployeeName().concat(ApplicationConstants.EMPLOYEE_UPDATE_SUCCESS));
		 }else {
			 employeeResponse.setErrorCode(ApplicationConstants.FAILURE_CODE);
			 employeeResponse.setMessage(employee.getEmployeeName().concat(ApplicationConstants.EMPLOYEE_UPDATE_FAILED));
		 }
		 logger.info("updateEmployee end");
		 return employeeResponse;
	 }
	 
	 @PostMapping(path = "/deleteEmployee")
	 @ResponseStatus(HttpStatus.OK)
	 public EmployeeResponse deleteEmployee(@RequestBody DeleteEmployeeRequest employee) {
		 logger.info("deleteEmployee Started");
		 EmployeeResponse employeeResponse = new EmployeeResponse();
		 int numberofRecords = employeesInfoService.deleteEmployee(employee.getId());
		 if(numberofRecords!=0) {
			 employeeResponse.setErrorCode(ApplicationConstants.SUCCESS_CODE);
			 employeeResponse.setMessage(employee.getId()+ " "+(ApplicationConstants.EMPLOYEE_DELETE_SUCCESS));
		 }else {
			 employeeResponse.setErrorCode(ApplicationConstants.FAILURE_CODE);
			 employeeResponse.setMessage(employee.getId()+ " "+ApplicationConstants.EMPLOYEE_DELETE_FAILED);
		 }
		 logger.info("deleteEmployee end");
		 return employeeResponse;
	 }

}
