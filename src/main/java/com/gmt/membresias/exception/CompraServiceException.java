package com.gmt.membresias.exception;

public class CompraServiceException extends Exception{
    private long id;

    public CompraServiceException(long id, String message) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
