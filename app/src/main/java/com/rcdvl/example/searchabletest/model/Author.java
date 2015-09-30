package com.rcdvl.example.searchabletest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by renan on 9/30/15.
 */
@Table(name = "authors")
public class Author extends Model {

    @Column
    public String firstName;
    @Column
    public String lastName;

    public List<Book> books() {
        return getMany(Book.class, "author");
    }
}
