package com.janmokrackirestproject.beans;

public class Book {
    protected String title;
    protected String author;
    protected int year;

    public Book(String title, String author, int year) {

        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }
}
