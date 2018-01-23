package com.packtpub.javaee8.integration;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * An general {@link ExceptionMapper} implementation for unknown RuntimeExceptions.
 */
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("code", "ERR-????");
        response.put("type", "GENERAL");
        response.put("message", exception.getMessage());

        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(response).type(MediaType.APPLICATION_JSON).build();
    }
}
