package com.rcdvl.example.searchabletest.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.provider.BaseColumns;

/**
 * Created by renan on 9/30/15.
 */
@Table(name = "books", id = BaseColumns._ID)
public class Book extends Model {

    @Column
    public String name;
    @Column
    public Author author;
}
