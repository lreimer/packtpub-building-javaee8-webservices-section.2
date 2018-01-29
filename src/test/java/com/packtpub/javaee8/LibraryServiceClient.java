package com.packtpub.javaee8;

import com.packtpub.javaee8.domain.Book;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.logging.Logger;

/**
 * A standalone JAX-RS client implementation for the
 * library service.
 */
public class LibraryServiceClient {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        // TODO implement me
    }

    private static GenericType<List<Book>> bookList() {
        return new GenericType<List<Book>>() {
        };
    }
}
