package com.gmt.membresias.exception;

public class EdoMembServiceException extends Exception{
    public static final long USER_NOT_FOUND = 10;
    private long id;
    
    
    public EdoMembServiceException(long id, String message) {
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
