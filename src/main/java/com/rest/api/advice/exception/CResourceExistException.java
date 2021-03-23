package com.rest.api.advice.exception;

public class CResourceExistException extends RuntimeException {
    public CResourceExistException(String msg, Throwable t) {
        super(msg, t);
    }
    public CResourceExistException(String msg) {
        super(msg);
    }
    public CResourceExistException() {
        super();
    }
}
