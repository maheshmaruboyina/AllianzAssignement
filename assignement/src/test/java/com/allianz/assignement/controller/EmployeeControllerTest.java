package com.allianz.assignement.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.allianz.assignement.entity.Employee;
import com.allianz.assignement.model.AuthenticationRequest;
import com.allianz.assignement.model.AuthenticationResponse;
import com.allianz.assignement.model.DeleteEmployeeRequest;
import com.allianz.assignement.model.EmployeeResponse;
import com.allianz.assignement.model.GetEmployeeResponse;
import com.allianz.assignement.model.GetEmployeesListResponse;
import com.allianz.assignement.utils.JwtTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {

Logger logger = LoggerFactory.getLogger(EmployeeControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	ObjectMapper mapper= new ObjectMapper();
	
	@Autowired
	WebApplicationContext context;
		
	@Autowired
	JwtTestUtils jwtTestUtils;
	
	/*
	 * Test Case for authenticate
	 */
	@Test
	@Order(0)
	public void authenticate() throws Exception {
		
		logger.info("authenticate Started");
		try {
			AuthenticationRequest authrequest = new AuthenticationRequest("mahesh", "mahesh");
			String request = mapper.writeValueAsString(authrequest);
			MvcResult result = mockMvc.perform(post("/authenticate")
					.content(request)
					.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk()).andReturn();
			AuthenticationResponse response = mapper.readValue(result.getResponse().getContentAsString(), AuthenticationResponse.class);
			logger.info("authenticate response : {}",result.getResponse().getContentAsString());
			assertEquals(response.getJwttoken()!=null,true);
			assertEquals(response.getJwttoken()==null,false);
			logger.info("authenticate end");
		}catch (Exception e) {
			logger.error("authenticate error : {}",e);
		}
	}
	
	/*
	 * Test Case for employees Info  success
	 */
	@Test
	@Order(1)
	public void getEmployee() throws Exception {
		logger.info("getEmployee Started");
		try {
			String empID = "1002";
			MvcResult result = mockMvc.perform(get("/employee/employeeInfo/"+empID))
					.andExpect(status().isOk()).andReturn();
			GetEmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), GetEmployeeResponse.class);
			logger.info("getEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("0", response.getErrorCode());
			logger.info("getEmployee end");
		}catch (Exception e) {
			logger.error("getEmployee error : {}",e);
		}
	}
	
	
	/*
	 * Test Case for employees Info  fail
	 */
	@Test
	public void getEmployeeFailed() throws Exception {
		logger.info("getEmployee Started");
		try {
			String empID = "1045";
			MvcResult result = mockMvc.perform(get("/employee/employeeInfo/"+empID))
					.andExpect(status().isOk()).andReturn();
			GetEmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), GetEmployeeResponse.class);
			logger.info("getEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("-1", response.getErrorCode());
			logger.info("getEmployee end");
		}catch (Exception e) {
			logger.error("getEmployee error : {}",e);
		}
	}
	
	/*
	 * Test Case for getEmployeeList
	 */
	@Test
	public void getEmployeeList() throws Exception {
		logger.info("getEmployeeList Started");
		try {
			MvcResult result = mockMvc.perform(get("/employee/employeesList/"))
					.andExpect(status().isOk()).andReturn();
			GetEmployeesListResponse response = mapper.readValue(result.getResponse().getContentAsString(), GetEmployeesListResponse.class);
			logger.info("getEmployeeList response : {}",result.getResponse().getContentAsString());
			assertEquals("0", response.getErrorCode());
			logger.info("getEmployeeList end");
		}catch (Exception e) {
			logger.error("getEmployeeList error : {}",e);
		}
	}
	
	/*
	 * Test Case for save employee
	 */
	@Test
	public void addEmployee() throws Exception {
		logger.info("addEmployee Test started");
		try {
			Employee employee = new Employee();
			employee.setEmployeeAge(36);
			employee.setEmployeeName("Test User");
			employee.setSalary("12000");
			String token = "";
			token = getJWTToken();
			String request = mapper.writeValueAsString(employee);
			MvcResult result = mockMvc.perform(post("/employee/addEmployee")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.header("Authorization", "Bearer ".concat(token))
					.content(request))
					.andExpect(status().isOk()).andReturn();
			EmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), EmployeeResponse.class);
			logger.info("addEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("0", response.getErrorCode());
			
		}catch (Exception e) {
			logger.error("addEmployee error : {}",e);
		}
	}
	
	/*
	 * Test Case for update Success
	 */
	@Test
	public void updateEmployee() throws Exception {
		logger.info("addEmployee Test started");
		try {
			Employee employee = new Employee();
			employee.setEmployeeAge(36);
			employee.setEmployeeName("Allianz User");
			employee.setSalary("120000");
			employee.setId(1003);
			String token = "";
			token = getJWTToken();
			String request = mapper.writeValueAsString(employee);
			MvcResult result = mockMvc.perform(post("/employee/updateEmployee")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.header("Authorization", "Bearer ".concat(token))
					.content(request))
					.andExpect(status().isOk()).andReturn();
			EmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), EmployeeResponse.class);
			logger.info("addEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("0", response.getErrorCode());
			
		}catch (Exception e) {
			logger.error("addEmployee error : {}",e);
		}
	}
	
	/*
	 * Test Case for update fail
	 */
	@Test
	public void updateEmployeeFailCase() throws Exception {
		logger.info("addEmployee Test started");
		try {
			Employee employee = new Employee();
			employee.setEmployeeAge(36);
			employee.setEmployeeName("Allianz User");
			employee.setSalary("120000");
			employee.setId(1025);
			String token = "";
			token = getJWTToken();
			String request = mapper.writeValueAsString(employee);
			MvcResult result = mockMvc.perform(post("/employee/updateEmployee")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.header("Authorization", "Bearer ".concat(token))
					.content(request))
					.andExpect(status().isOk()).andReturn();
			EmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), EmployeeResponse.class);
			logger.info("addEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("-1", response.getErrorCode());
			
		}catch (Exception e) {
			logger.error("addEmployee error : {}",e);
		}
	}
	
	/*
	 * Test Case for delete success
	 */
	@Test
	public void deleteEmployee() throws Exception {
		logger.info("addEmployee Test started");
		try {
			DeleteEmployeeRequest employee = new DeleteEmployeeRequest();
			employee.setId(1001);
			String token = "";
			token = getJWTToken();
			String request = mapper.writeValueAsString(employee);
			MvcResult result = mockMvc.perform(post("/employee/deleteEmployee")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.header("Authorization", "Bearer ".concat(token))
					.content(request))
					.andExpect(status().isOk()).andReturn();
			EmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), EmployeeResponse.class);
			logger.info("addEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("0", response.getErrorCode());
			
		}catch (Exception e) {
			logger.error("addEmployee error : {}",e);
		}
	}
	
	/*
	 * Test Case for delete Fail
	 */
	public void deleteEmployeeFail() throws Exception {
		logger.info("addEmployee Test started");
		try {
			DeleteEmployeeRequest employee = new DeleteEmployeeRequest();
			employee.setId(1090);
			String token = "";
			token = getJWTToken();
			String request = mapper.writeValueAsString(employee);
			MvcResult result = mockMvc.perform(post("/employee/deleteEmployee")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.header("Authorization", "Bearer ".concat(token))
					.content(request))
					.andExpect(status().isOk()).andReturn();
			EmployeeResponse response = mapper.readValue(result.getResponse().getContentAsString(), EmployeeResponse.class);
			logger.info("addEmployee response : {}",result.getResponse().getContentAsString());
			assertEquals("0", response.getErrorCode());
			
		}catch (Exception e) {
			logger.error("addEmployee error : {}",e);
		}
	}
	
	private String getJWTToken() throws Exception {
		AuthenticationRequest authrequest = new AuthenticationRequest("allianz", "allianz");
		String request = mapper.writeValueAsString(authrequest);
		MvcResult result = mockMvc.perform(post("/authenticate")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		AuthenticationResponse response = mapper.readValue(result.getResponse().getContentAsString(), AuthenticationResponse.class);
		return response.getJwttoken();
	}
}
