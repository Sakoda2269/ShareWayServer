package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="STEP")
@IdClass(StepPK.class)
public class Step {


	@Id
	@Column(name="manual_id")
	private String manualId;
	
	@Id
	@Column(name="step_num")
	private Integer stepNum;
	
	@Column(name="title")
	private String title;
	
	@Column(name="detail")
	private String detail;
	
	private byte[] picture;
	
	public Step() {}
	
	public Step(String manualId, Integer stepNum, String title, String detail) {
		this.manualId = manualId;
		this.stepNum = stepNum;
		this.title = title;
		this.detail = detail;
	}
	
	public Step(String manualId, Integer stepNum, String title, String detail, byte[] picture) {
		this.manualId = manualId;
		this.stepNum = stepNum;
		this.title = title;
		this.detail = detail;
		this.picture = picture;
	}
	
}
