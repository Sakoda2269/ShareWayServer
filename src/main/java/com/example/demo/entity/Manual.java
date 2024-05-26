package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="MANUAL")
public class Manual {
	
	@Id
	@Column(name="manual_id")
	private String manualId;
	
	private String title;
	
	@Column(name="account_id")
	private String accountId;
	
	private byte[] thumbnail;
	
	public Manual(String manualId, String title, String accountId) {
		this.manualId = manualId;
		this.title = title;
		this.accountId = accountId;
	}
	
	public Manual(String manualId, String title, String accountId, byte[] thumbnail) {
		this.manualId = manualId;
		this.title = title;
		this.accountId = accountId;
		this.thumbnail = thumbnail;
	}
	
	
	public Manual() {};
	
}
