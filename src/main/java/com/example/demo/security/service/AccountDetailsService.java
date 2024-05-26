package com.example.demo.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountDetailsService implements UserDetailsService{
	
	private final AccountRepository repository;
	
	public AccountDetailsService(AccountRepository repo) {
		this.repository = repo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = repository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException("Account not found"));
		return new AccountDetails(account);
	}
	
}
