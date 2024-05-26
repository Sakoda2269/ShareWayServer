package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class StepPK implements Serializable{
	
	@Column(name="manual_id")
	private String manualId;
	
	@Column(name="step_num")
	private Integer stepNum;
	
}
