package org.rentifytools.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message) {
        super("Email " + message + " already exists");
    }
}
