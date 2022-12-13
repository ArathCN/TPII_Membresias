package com.gmt.membresias.models;

public class Membresia {
    private long id;
    private String descripcion;
    private double precio;
    private int duracion; //en días
    
    public Membresia() {
    }

    public Membresia(long id, String descripcion, double precio, int duracion) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracion = duracion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    
}