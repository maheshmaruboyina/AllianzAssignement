package com.allianz.assignement.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
	private String jwttoken;
	private static final long serialVersionUID = 1L;
	
	public AuthenticationResponse() {
		
	}
	
	public AuthenticationResponse(String jwttoken) {
		super();
		this.jwttoken = jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	public String getJwttoken() {
		return jwttoken;
	}
}
