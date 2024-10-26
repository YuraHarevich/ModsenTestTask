package ru.modsen.exception;

import lombok.Getter;
import ru.modsen.model.Book;

public class BookPublishException extends RuntimeException {

    @Getter
    private Book book;
    public BookPublishException(String message, Throwable cause, final Book book) {
        super(message, cause);
        this.book  = book;
    }

}
