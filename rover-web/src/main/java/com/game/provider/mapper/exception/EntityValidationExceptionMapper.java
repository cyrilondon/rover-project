package com.game.provider.mapper.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.game.domain.model.exception.EntityValidationException;

@Provider
public class EntityValidationExceptionMapper implements ExceptionMapper<EntityValidationException> {

	@Override
	public Response toResponse(EntityValidationException exception) {
		return Response.status(500).entity(exception.getMessage()).type("text/plain").build();
	}

}
