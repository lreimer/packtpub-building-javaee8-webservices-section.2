package com.packtpub.javaee8.integration;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link ExceptionMapper} implementation for all JPA {@link PersistenceException}s.
 */
@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {
    @Override
    public Response toResponse(PersistenceException exception) {
        if (exception instanceof EntityNotFoundException) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("code", "ERR-4711");
            response.put("type", "DATABASE");
            response.put("message", exception.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(response).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
