package com.packtpub.javaee8;

import com.packtpub.javaee8.domain.Author;
import com.packtpub.javaee8.domain.Book;
import com.packtpub.javaee8.domain.Loan;
import org.glassfish.jersey.jsonb.JsonBindingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A standalone JAX-RS client implementation for the
 * library service.
 */
public class LibraryServiceTestClient {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        // construct a JAX-RS client using the builder
        Client client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .register(JsonBindingFeature.class)
                .build();

        // construct a web target for the library service
        WebTarget api = client.target("http://localhost:8080").path("/library-service/api");

        LOGGER.log(Level.INFO, "Get list of books.");
        List<Book> books = api.path("/books").request().accept(MediaType.APPLICATION_JSON).get(bookList());
        books.forEach(book -> LOGGER.log(Level.INFO, "{0}", book));

        LOGGER.log(Level.INFO, "Get unknown book by ISBN.");
        Response response = api.path("/books").path("/{isbn}").resolveTemplate("isbn", "1234567890")
                .request().accept(MediaType.APPLICATION_JSON).get();
        assert response.getStatus() == 201;

        Book book = new Book("1234567890", "Building Web Services with Java EE 8",
                new Author("M.-Leander Reimer"));

        LOGGER.log(Level.INFO, "Creating new {0}.", book);
        response = api.path("/books").request(MediaType.APPLICATION_JSON).post(Entity.json(book));
        assert response.getStatus() == 201;

        URI bookUri = response.getLocation();
        LOGGER.log(Level.INFO, "Get created book with URI {0}.", bookUri);
        Book createdBook = client.target(bookUri).request().accept(MediaType.APPLICATION_JSON).get(Book.class);
        assert book.equals(createdBook);

        Loan loan = new Loan("mario-leander.reimer", LocalDate.now(), LocalDate.now().plusMonths(1));
        LOGGER.log(Level.INFO, "Create new {0}.", loan);
        response = client.target(bookUri).path("/loans").request(MediaType.APPLICATION_JSON).post(Entity.json(loan));
        assert response.getStatus() == 201;

        URI loanUri = response.getLocation();
        LOGGER.log(Level.INFO, "Get created loan with URI {0}.", loanUri);
        Loan createdLoan = client.target(loanUri).request().accept(MediaType.APPLICATION_JSON).get(Loan.class);
        assert loan.equals(createdLoan);

        LOGGER.log(Level.INFO, "Delete book with URI {0}.", bookUri);
        response = client.target(bookUri).request().delete();
        assert response.getStatus() == 200;

        client.close();
    }

    private static GenericType<List<Book>> bookList() {
        return new GenericType<List<Book>>() {
        };
    }
}
