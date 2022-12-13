package com.gmt.membresias.exception;

public class ClienteServiceException extends Exception{
    private long id;

    public ClienteServiceException(long id, String message) {
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
