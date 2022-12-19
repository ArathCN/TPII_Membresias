package com.gmt.membresias.models;

public class Respuesta {
    private Transaccion response;

    public Respuesta(){
        
    }
    public Respuesta(Transaccion response) {
        this.response = response;
    }
    public Transaccion getResponse() {
        return response;
    }
    public void setResponse(Transaccion response) {
        this.response = response;
    }

    
}
