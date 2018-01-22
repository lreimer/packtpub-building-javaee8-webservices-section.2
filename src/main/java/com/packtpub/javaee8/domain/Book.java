package com.packtpub.javaee8.domain;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The main book entity.
 */
@Entity
@Table(name = "book")
@NamedQuery(name = Book.FIND_ALL, query = "SELECT b FROM Book b")
@JsonbPropertyOrder(value = {"isbn", "title", "author"})
public class Book {
    static final String FIND_ALL = "Book.findAll";

    @Id
    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Embedded
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Loan> loans = new ArrayList<>();

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan) {
        loan.setBook(this);
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setBook(null);
    }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", author=" + author + '}';
    }
}
