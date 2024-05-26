package com.example.demo.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

public class JwtRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter{
	
	public JwtRequestHeaderAuthenticationFilter(AuthenticationManager authenticationManger) {
		
		setPrincipalRequestHeader("Authorization");
		setExceptionIfHeaderMissing(false);
		setAuthenticationManager(authenticationManger);
		
	}
	
}
