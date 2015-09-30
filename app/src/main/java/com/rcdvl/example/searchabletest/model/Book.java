package com.rcdvl.example.searchabletest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by renan on 9/30/15.
 */
@Table(name = "books")
public class Book extends Model {

    @Column
    public String name;
    @Column
    public Author author;
}
