package com.packtpub.javaee8.boundary;

import com.packtpub.javaee8.domain.Book;
import com.packtpub.javaee8.domain.Bookshelf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Logger;

/**
 * The Book REST resource implementation.
 */
@Path("books")
@RequestScoped
public class BookResource {

    @Inject
    private Bookshelf bookshelf;
    @Context
    private ResourceContext context;
    @Inject
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response books() {
        return Response.ok(bookshelf.findAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book) {
        bookshelf.create(book);
        URI location = UriBuilder.fromResource(BookResource.class)
                .path("/{isbn}")
                .resolveTemplate("isbn", book.getIsbn())
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{isbn}")
    public Response get(@PathParam("isbn") String isbn) {
        Book book = bookshelf.findByISBN(isbn);
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book) {
        // TODO check of ISBN matches the Books ISBN
        bookshelf.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn) {
        bookshelf.delete(isbn);
        return Response.ok().build();
    }

    @Path("/{isbn}/author")
    public AuthorResource author(@PathParam("isbn") String isbn) {
        Book book = bookshelf.findByISBN(isbn);
        return new AuthorResource(book);
    }

    @Path("/{isbn}/loans")
    public LoanResource loans(@PathParam("isbn") String isbn) {
        logger.info("Initialize and return Subresource locator for Loans.");

        LoanResource loanResource = context.getResource(LoanResource.class);
        loanResource.setIsbn(isbn);

        return loanResource;
    }
}
