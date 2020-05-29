package com.allianz.assignement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.allianz.assignement.config.JwtUtil;
import com.allianz.assignement.service.UserDetailInfoService;

@Component
public class JwtTestUtils {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailInfoService userDetailsService;
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	public String getToken() throws Exception{
		authenticate("mahesh","mahesh");
		UserDetails userDetails = userDetailsService.loadUserByUsername("mahesh");
		final String token = jwtUtil.generateToken(userDetails);
		System.out.println(token);
		return token;
	}
}
