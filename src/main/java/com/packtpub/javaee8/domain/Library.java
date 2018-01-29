package com.packtpub.javaee8.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Library implementation supports lending books.
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class Library {
    @Inject
    private EntityManager entityManager;
    @Inject
    private Logger logger;

    /**
     * Find a loan by ID.
     *
     * @param loadId the loan ID
     * @return the loan
     */
    public Loan getLoan(String loadId) {
        Loan loan = entityManager.getReference(Loan.class, loadId);
        return loan;
    }

    /**
     * Return a book with given ISBN on given load.
     *
     * @param isbn   the ISBN
     * @param loanId the loan ID
     */
    public void returnBook(String isbn, String loanId) {
        logger.log(Level.INFO, "Returning book with ISBN {0} on loan ID {1}.", new Object[]{isbn, loanId});
        Book book = entityManager.getReference(Book.class, isbn);
        book.removeLoan(new Loan(loanId));
    }

    /**
     * Lend a book with given ISBN and create a new loan on it.
     *
     * @param isbn the book ISBN
     * @param loan the loan info
     */
    public void lendBook(String isbn, Loan loan) {
        logger.log(Level.INFO, "Lend book with ISBN {0} on {1}.", new Object[]{isbn, loan});
        Book book = entityManager.getReference(Book.class, isbn);
        book.addLoan(loan);
    }
}
