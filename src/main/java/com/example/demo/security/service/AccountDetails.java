package com.example.demo.security.service;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.entity.Account;

import lombok.Getter;

@Getter
public class AccountDetails extends User{

	private final Account account;
	
	public AccountDetails(Account account) {
		super(account.getAccountName(), account.getPassword(), 
				Arrays.asList(account.getRoles().split(",")).stream().map(
						role -> new SimpleGrantedAuthority((role))
				).toList()
		);
		this.account = account;
	}
	
	
}
