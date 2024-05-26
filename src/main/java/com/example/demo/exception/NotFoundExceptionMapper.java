package com.example.demo.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFound>{
	
	@Override
	public Response toResponse(NotFound ex) {
		return Response.status(Response.Status.NOT_FOUND)
				.entity(ex.getMessage())
				.build();
	}

}
