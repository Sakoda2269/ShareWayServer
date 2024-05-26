package com.example.demo.resources;

import java.io.IOException;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Account;
import com.example.demo.exception.BadRequest;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Component
@Path("/accounts")
public class AccountRest {
	
	private final AccountService accountService;
	
	public AccountRest(AccountRepository ar, AccountService as) {
		this.accountService = as;
	}
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Account createAccount(@FormParam("name") String name, @FormParam("password") String password, @FormParam("email") String email) {
		return accountService.signup(name, password, email);
	}
	
	@GET
	@Path("/{account_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("account_id") String accountId) {
		return accountService.getAccountById(accountId);
	}
	
	@PUT
	@Path("/{account_id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void changeAccountInfo(@PathParam("account_id") String accountId, @FormParam("account_name") String newName,
			@FormParam("introduction") String newIntro) {
		accountService.authorizationCheck(accountId);
		accountService.changeAccountInfo(accountId, newName, newIntro);
	}
	
	@PUT
	@Path("/{account_id}/icon")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void changeAccount(@PathParam("account_id") String accountId,
			@FormDataParam("new_icon") InputStream fileStream, @FormDataParam("new_icon") FormDataContentDisposition fileDisposition) {
		accountService.authorizationCheck(accountId);
		try {
			accountService.changeIcon(accountId, fileStream.readAllBytes());
		} catch (IOException e) {
			throw new BadRequest("invalid image data");
		}
	}
	
	@GET
	@Path("/{account_id}/icon")
	@Produces("image/png")
	public byte[] getAccountIcon(@PathParam("account_id") String accountId) {
		return accountService.getIcon(accountId);
	}
	
}
