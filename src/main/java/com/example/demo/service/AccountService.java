package com.example.demo.service;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.exception.Conflict;
import com.example.demo.exception.Forbidden;
import com.example.demo.exception.NotFound;
import com.example.demo.repository.AccountRepository;
import com.example.demo.security.service.AccountDetails;

@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AccountService(AccountRepository ar, PasswordEncoder pe){
		this.accountRepository = ar;
		this.passwordEncoder = pe;
	}
	
	public boolean authorizationCheck(String id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = ((AccountDetails) principal).getAccount();
		if(account == null) {
			throw new Forbidden("forbidden");
		}
		if(!account.getAccountId().equals(id)) {
			throw new Forbidden("forbidden");
		}
		return true;
	}
	
	public Account signup(String name, String password, String email) {
		if (accountRepository.findByEmail(email).isPresent()) {
			throw new Conflict("conflict");
		}
		String uuid = UUID.randomUUID().toString();
		Account newAccount = new Account(uuid, name, passwordEncoder.encode(password), email);
		accountRepository.save(newAccount);
		return newAccount;
	}
	
	public Account getAccountById(String id){
		return accountRepository.findById(id).orElseThrow(
				() -> new NotFound("not found")
		);
	}
	
	public void changeAccountInfo(String id, String name, String intoro) {
		Account account = getAccountById(id);
		account.setAccountName(name);
		account.setIntroduction(intoro);
		accountRepository.save(account);
	}
	
	public void changeIcon(String id, byte[] icon) {
		Account account = getAccountById(id);
		account.setIcon(icon);
		accountRepository.save(account);
	}
	
	public byte[] getIcon(String id) {
		return getAccountById(id).getIcon();
	}
	
}
