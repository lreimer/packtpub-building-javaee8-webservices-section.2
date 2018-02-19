package com.packtpub.javaee8.integration;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * An {@link ExceptionMapper} implementation for all JPA {@link PersistenceException}s.
 */
// TODO add provider annotation
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {
    @Override
    public Response toResponse(PersistenceException exception) {
        // TODO implement me
        return null;
    }
}
