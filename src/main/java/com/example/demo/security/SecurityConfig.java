package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.security.filter.JwtCreateFilter;
import com.example.demo.security.filter.JwtRequestHeaderAuthenticationFilter;
import com.example.demo.security.service.AccountDetailsService;
import com.example.demo.security.service.AuthenticationAccountDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	@Autowired
	public void configureProvider(
			AuthenticationManagerBuilder auth,
			AccountDetailsService accountDetailsService,
			AuthenticationAccountDetailService autehnticationAccountDetailService
		) throws Exception {
		PreAuthenticatedAuthenticationProvider preAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
		preAuthenticationProvider.setPreAuthenticatedUserDetailsService(autehnticationAccountDetailService);
		preAuthenticationProvider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
		auth.authenticationProvider(preAuthenticationProvider);
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(accountDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(8));
		auth.authenticationProvider(daoAuthenticationProvider);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration autheticationConfiguration) throws Exception {
		return autheticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}
	
	AntPathRequestMatcher path(String path) {
		return AntPathRequestMatcher.antMatcher(path);
	}
	
	AntPathRequestMatcher path(HttpMethod method, String path) {
		return AntPathRequestMatcher.antMatcher(method, path);
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		AuthenticationManager authManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
		
		http
			.securityMatcher(path("/h2-console/**"))
			.authorizeHttpRequests(authz -> authz
					.requestMatchers(path("/h2-console/**")).permitAll()//denyAll()
			)
			.csrf(csrf -> csrf
					.ignoringRequestMatchers(path("/h2-console/**"))
			)
			.headers(headers -> headers.frameOptions(
					frame -> frame.sameOrigin())
			)
			
			.securityMatcher(path("/accounts/**"))
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authz -> authz
					.requestMatchers(path("/accounts/signup")).permitAll()
					.requestMatchers(path("/accounts/login")).permitAll()
					.requestMatchers(path("/accounts/{account_id}")).permitAll()
					.requestMatchers(HttpMethod.GET, "/accounts/{account_id}/manuals").permitAll()
					.requestMatchers(HttpMethod.POST, "/accounts/{account_id}/manuals").authenticated()
					.requestMatchers(HttpMethod.PUT, "/accounts/{account_id}/icon").authenticated()
//					.requestMatchers(HttpMethod.PUT, "accounts/**").authenticated()
			)
			.csrf(csrf -> csrf
					.ignoringRequestMatchers(path("/accounts/**"))
			)
			.headers(headers -> headers.frameOptions(
					frame -> frame.sameOrigin())
			)
			.addFilter(new JwtCreateFilter(authManager))
			.addFilter(new JwtRequestHeaderAuthenticationFilter(authManager))
			
			
			.securityMatcher(path("/**"))
			.authorizeHttpRequests(authz -> authz
					.requestMatchers(path("/**")).permitAll()
			)
			.csrf(csrf -> csrf
					.ignoringRequestMatchers(path("/**"))
			)
			.headers(headers -> headers.frameOptions(
					frame -> frame.sameOrigin())
			)
			.addFilter(new JwtCreateFilter(authManager))
			.addFilter(new JwtRequestHeaderAuthenticationFilter(authManager))
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			;
		return http.build();
	}
}
