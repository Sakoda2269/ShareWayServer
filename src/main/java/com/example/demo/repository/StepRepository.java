package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Step;
import com.example.demo.entity.StepPK;

public interface StepRepository extends JpaRepository<Step, StepPK>{
	
	public Optional<List<Step>> findByManualId(String manualId);
	
}
