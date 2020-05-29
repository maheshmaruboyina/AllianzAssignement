package com.allianz.assignement.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.allianz.assignement.config.JwtUtil;
import com.allianz.assignement.service.UserDetailInfoService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
	private UserDetailInfoService userDetailInfoService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("doFilterInternal started");
		final String requestJWT = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		String path = request.getRequestURL().toString();
		logger.info("doFilterInternal path : {}",path);	
		if (requestJWT != null && requestJWT.startsWith("Bearer ")) {
			jwtToken = requestJWT.substring(7);
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.info("Unable to Fetch JWT Token");
			} catch (ExpiredJwtException e) {
				logger.info("JWT Token expired");
			}
		} else {
			logger.info("JWT Token Not in header");
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailInfoService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		logger.info("doFilterInternal end");
		filterChain.doFilter(request, response);
	}
}