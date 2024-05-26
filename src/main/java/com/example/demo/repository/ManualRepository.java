package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Manual;

public interface ManualRepository extends JpaRepository<Manual, String>{
	
	public Optional<List<Manual>> findByAccountId(String accountId);
	
	public Optional<Manual> findByManualId(String manualId);
}
