package com.example.demo.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.exception.BadRequest;
import com.example.demo.service.AccountService;
import com.example.demo.service.ManualService;
import com.example.demo.service.ManualService.RequestManual;
import com.example.demo.service.ManualService.ResponseManual;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Component
@Path("/accounts/{account_id}/manuals")
public class ManualRest {
	
	private final ManualService manualService;
	private final AccountService accountService;
	
	public ManualRest(ManualService ms, AccountService as) {
		manualService = ms;
		accountService = as;
	}

	@POST
	public void createNewManual(@PathParam("account_id") String accountId, @RequestBody RequestManual manual) {
		accountService.authorizationCheck(accountId);
		manualService.createManual(accountId, manual);
	}
	
	/*
	 * [
	 * 		["image", ["manual", manual_image: byte[], "image/png"]],
	 * 		["image", ["step0", step0_image: byte[], "image/png"]],
	 * 		["image", ["step1", step1_image: byte[], "image/png"]],
	 * 		...,
	 * 		["data", ["manual", RequestManual: json, "application/json"]]
	 * ]
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void createNewManual(@PathParam("account_id") String accountId, 
			@FormDataParam("data") FormDataBodyPart manualBody, @FormDataParam("image") List<FormDataBodyPart> images) {
		RequestManual manual = manualBody.getContent(RequestManual.class);
		Map<String, byte[]> imageMap = new HashMap<>();
		for(FormDataBodyPart part : images) {
			try {
				imageMap.put(part.getFileName().get(), part.getContent().readAllBytes());
			} catch(IOException e) {
				throw new BadRequest("bad request");
			}
		}
		accountService.authorizationCheck(accountId);
		manualService.createManual(accountId, manual, imageMap);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, ResponseManual> getAllManuals(@PathParam("account_id") String accountId) {
		return manualService.getAllManuals(accountId);
	}
	
	@GET
	@Path("/{manual_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseManual getManualById(@PathParam("manual_id") String manualId) {
		return manualService.getManualById(manualId, manualId);
	}
	
}


