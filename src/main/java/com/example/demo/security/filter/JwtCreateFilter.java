package com.example.demo.security.filter;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.security.service.AccountDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtCreateFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	public JwtCreateFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/accounts/login", "POST"));
		
		//認証成功
		this.setAuthenticationSuccessHandler((req, res, ex) -> {
			Date issuedAt = new Date();
			Object principal = ex.getPrincipal();
			String id = ((AccountDetails) principal).getAccount().getAccountId();
			String token = JWT.create()
					.withIssuer("tako")
					.withIssuedAt(issuedAt)
					.withExpiresAt(new Date(issuedAt.getTime() + 1000 * 60 * 60))
					.withClaim("name", ex.getName())
					.withClaim("id", id)
					.withClaim("roles", ex.getAuthorities().iterator().next().toString())
					.sign(Algorithm.HMAC256("secret"));
			res.setHeader("X-AUTH-TOKEN", token);
			res.setHeader("id", id);
			res.setStatus(200);
		});
		
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
		);
	}

}
