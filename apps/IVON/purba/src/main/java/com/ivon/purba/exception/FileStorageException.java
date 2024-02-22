package com.ivon.purba.exception;

public class FileStorageException extends RuntimeException {
    public FileStorageException() {}
    public FileStorageException(String message) {
        super(message);
    }
}
