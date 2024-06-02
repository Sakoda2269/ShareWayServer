package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Table(name="ACCOUNT")
@Data
public class Account {
	
	@Id
	@Column(name="account_id")
	private String accountId;
	
	@Column(name="account_name", nullable = false, length = 50)
	private String accountName;
	
	@JsonIgnore
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@JsonIgnore
	@Column(name="roles")
	private String roles;
	
	@Column(name="introduction")
	private String introduction;
	
	@JsonIgnore
	@Column(name="icon")
	private byte[] icon;
	
	public Account() {};
	
	public Account(String id, String name, String password_encoded, String email) {
		accountId = id;
		accountName = name;
		password = password_encoded;
		this.email = email;
		this.roles = "ROLE_GENERAL";
	};
	
}
