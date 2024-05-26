package com.example.demo.entity;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Table("IMAGE")
public class ImageTest {
	
	private byte[] image;
	
	@Id
	private Integer id;

}
