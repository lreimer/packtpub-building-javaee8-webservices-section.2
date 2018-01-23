package com.packtpub.javaee8.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * JPA unit test for the Bookshelf.
 */
public class BookshelfTest {

    private Bookshelf bookshelf;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("books-test");
        entityManager = entityManagerFactory.createEntityManager();
        bookshelf = new Bookshelf(entityManager, Logger.getAnonymousLogger());
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void findAll() {
        Collection<Book> books = bookshelf.findAll();
        assertThat(books, hasSize(4));
    }

    @Test
    public void findByISBN() {
        Book hitchhikerGuide = bookshelf.findByISBN("0345391802");
        assertThat(hitchhikerGuide, is(notNullValue()));
        assertThat(hitchhikerGuide.getAuthor().getName(), equalTo("Douglas Adams"));
    }

    @Test
    public void createBook() {
        Book book = new Book("1234567890", "Java EE 8 Testing", new Author("mario-leander.reimer"));
        bookshelf.create(book);

        Book created = bookshelf.findByISBN("1234567890");
        assertThat(book, equalTo(created));
    }
}