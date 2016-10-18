package com.gleidesilva.bookslibrary;

/**
 * Created by gleides on 17/10/16.
 */
public class BookManager {

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book  book;

    private static BookManager ourInstance = new BookManager();

    public static BookManager getInstance() {
        return ourInstance;
    }

    private BookManager() {
    }
}
