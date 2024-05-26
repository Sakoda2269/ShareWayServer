package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Manual;
import com.example.demo.entity.Step;
import com.example.demo.exception.NotFound;
import com.example.demo.repository.ManualRepository;
import com.example.demo.repository.StepRepository;

@Service
public class ManualService {

	private final ManualRepository manualRepository;
	private final StepRepository stepRepository;
	private final AccountService accountService;
	
	public ManualService(ManualRepository manualRepo, StepRepository stepRepo, AccountService accountService){
		this.manualRepository = manualRepo;
		this.accountService = accountService;
		this.stepRepository = stepRepo;
	}
	
	public void createManual(String accountId, RequestManual manual) {
		Account account = accountService.getAccountById(accountId);
		String manualId = UUID.randomUUID().toString();
		Manual newManual = new Manual(manualId, manual.title, account.getAccountId());
		manualRepository.save(newManual);
		for(RequestStep step : manual.steps) {
			stepRepository.save(new Step(manualId, step.stepNum, step.title, step.detail));
		}
	}
	
	public void createManual(String accountId, RequestManual manual, Map<String, byte[]> images) {
		Account account = accountService.getAccountById(accountId);
		String manualId = UUID.randomUUID().toString();
		Manual newManual = new Manual(manualId, manual.title, account.getAccountId(), images.get("manual"));
		manualRepository.save(newManual);
		for(RequestStep step : manual.steps) {
			stepRepository.save(new Step(manualId, step.stepNum, step.title, step.detail, images.get("step" + step.stepNum)));
		}
	}
	
	public Map<String, ResponseManual> getAllManuals(String accountId){
		Map<String, ResponseManual> response = new HashMap<>();
		Account account = accountService.getAccountById(accountId);
		List<Manual> manuals = manualRepository.findByAccountId(accountId).orElseThrow(() -> 
			new NotFound("not found")
		);
		
		for(Manual manual : manuals) {
			List<RequestStep> steps = new ArrayList<>();
			Optional<List<Step>> stepData = stepRepository.findByManualId(manual.getManualId());
			if(stepData.isPresent()) {
				for(Step step: stepData.get()) {
					steps.add(new RequestStep(step.getStepNum(), step.getTitle(), step.getDetail()));
				}
			}
			ResponseManual res = new ResponseManual(accountId, account.getAccountName(), manual.getTitle(), steps);
			response.put(manual.getManualId(), res);
		}
		
		return response;
	}
	
	public ResponseManual getManualById(String accountId, String manualId) {
		Account account = accountService.getAccountById(accountId);
		Manual manual = manualRepository.findByManualId(manualId).orElseThrow(() -> 
			new NotFound("not found")
		);
		List<RequestStep> steps = new ArrayList<>();
		Optional<List<Step>> stepData = stepRepository.findByManualId(manual.getManualId());
		if(stepData.isPresent()) {
			for(Step step: stepData.get()) {
				steps.add(new RequestStep(step.getStepNum(), step.getTitle(), step.getDetail()));
			}
		}
		return new ResponseManual(accountId, account.getAccountName(), manual.getTitle(), steps);
	}

	public record RequestManual(String title, List<RequestStep> steps) {};
	public record RequestStep(Integer stepNum, String title, String detail) {};
	public record ResponseManual(String accountId, String accountName, String title, List<RequestStep> steps) {};
	
}
