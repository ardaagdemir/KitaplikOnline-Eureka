package com.kitaplik.libraryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LibraryNotFoundException extends RuntimeException {

    private ExceptionMessage exceptionMessage;
    public LibraryNotFoundException(String message) {
        super(message);
    }

    public LibraryNotFoundException(ExceptionMessage message) {
        this.exceptionMessage = message;
    }

    public LibraryNotFoundException(String message, ExceptionMessage exceptionMessage) {
        super(message);
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }
}
