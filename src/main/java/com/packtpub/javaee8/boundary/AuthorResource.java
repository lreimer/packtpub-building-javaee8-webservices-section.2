package com.packtpub.javaee8.boundary;

import com.packtpub.javaee8.domain.Author;
import com.packtpub.javaee8.domain.Book;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Simple Sub Resource for the author of a book.
 */
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private final Book book;

    AuthorResource(Book book) {
        this.book = book;
    }

    @GET
    public Author get() {
        return book.getAuthor();
    }
}
