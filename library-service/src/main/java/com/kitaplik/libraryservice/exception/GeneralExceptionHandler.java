package com.kitaplik.libraryservice.exception;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

/*
Uygulama sırasında fırlatılan bir exception' ı Rest Response' a çevirmek için yazılan bir sınıftır.
 */

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<?> exceptionHandleLibraryNotFound(LibraryNotFoundException exception) {
        return new ResponseEntity<>(exception.getExceptionMessage(), HttpStatus.resolve(exception.getExceptionMessage().status()));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionMessage> feignDecoderExceptionHandleBookNotFound(BookNotFoundException exception) {
        return new ResponseEntity<>(exception.getExceptionMessage(), HttpStatus.resolve(exception.getExceptionMessage().status()));
    }

}
