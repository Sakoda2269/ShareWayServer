package com.example.demo.repository;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImageTestRepository{
	
	private final JdbcTemplate jdbc;
	private int count = 0;
	
	ImageTestRepository(JdbcTemplate jdbc){
		this.jdbc = jdbc;
	}
	
	public byte[] uploadFile(byte[] bytes) {
			jdbc.update("INSERT INTO IMAGE VALUES (?, ?)", count, bytes);
			count++;
			return bytes;
	}
	
	public Map<String, Object> getImageById(Integer id) {
		return jdbc.queryForMap("SELECT * FROM IMAGE WHERE ID = ?", id);
	}
	
	
	
}
