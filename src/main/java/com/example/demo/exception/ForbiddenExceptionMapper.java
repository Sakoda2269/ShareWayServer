package com.example.demo.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ForbiddenExceptionMapper implements ExceptionMapper<Forbidden>{

	@Override
	public Response toResponse(Forbidden exception) {
		return Response.status(Response.Status.FORBIDDEN)
				.entity(exception.getMessage())
				.build();
	}

}
