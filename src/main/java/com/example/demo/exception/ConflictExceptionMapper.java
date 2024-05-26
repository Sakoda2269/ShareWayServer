package com.example.demo.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ConflictExceptionMapper implements ExceptionMapper<Conflict>{

	@Override
	public Response toResponse(Conflict exception) {
		return Response.status(Response.Status.CONFLICT)
				.entity(exception.getMessage())
				.build();
	}

}
