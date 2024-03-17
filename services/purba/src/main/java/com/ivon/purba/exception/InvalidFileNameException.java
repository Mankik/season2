package com.ivon.purba.exception;

public class InvalidFileNameException extends RuntimeException {
    public InvalidFileNameException() {}
    public InvalidFileNameException(String message) {
        super(message);
    }

}
