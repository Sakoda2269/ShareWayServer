package com.example.demo.security.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.Account;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationAccountDetailService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>{
	
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		DecodedJWT decodedJwt;
		try {
			decodedJwt = JWT.require(Algorithm.HMAC256("secret")).build().verify(token.getPrincipal().toString());
		} catch(JWTDecodeException ex) {
			throw new BadCredentialsException("Authorization header token is invalid");
		}
		
		if (decodedJwt.getToken().isEmpty()) {
			throw new UsernameNotFoundException("Authorization header must not be empty");
		}
		log.info(decodedJwt.getToken());
		Account account = new Account();
		account.setAccountName(decodedJwt.getClaim("name").asString());
		account.setPassword("");
		account.setAccountId(decodedJwt.getClaim("id").asString());
		account.setRoles(decodedJwt.getClaim("roles").asString());
		return new AccountDetails(account);
		
	}

}
