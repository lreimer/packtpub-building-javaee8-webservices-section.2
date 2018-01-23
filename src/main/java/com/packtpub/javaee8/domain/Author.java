package com.packtpub.javaee8.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A simple embeddable POJO modelling an Author.
 */
@Embeddable
public class Author {

    @Column(name = "author", nullable = false)
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{name='" + name + '\'' + '}';
    }
}
