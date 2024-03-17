package com.ivon.purba.exception;

import java.io.IOException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {}
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
