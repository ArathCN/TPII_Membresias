package com.gmt.membresias.models;

public class Tarjeta {
    private long numero;
    private int mesVencimiento;
    private int anioVencimiento;
    private int codigo;

    public Tarjeta(){
        
    }
    
    public Tarjeta(long numero, int mesVencimiento, int anioVencimiento, int codigo) {
        this.numero = numero;
        this.mesVencimiento = mesVencimiento;
        this.anioVencimiento = anioVencimiento;
        this.codigo = codigo;
    }

    public long getNumero() {
        return numero;
    }
    public void setNumero(long numero) {
        this.numero = numero;
    }
    public int getMesVencimiento() {
        return mesVencimiento;
    }
    public void setMesVencimiento(int mesVencimiento) {
        this.mesVencimiento = mesVencimiento;
    }
    public int getAnioVencimiento() {
        return anioVencimiento;
    }
    public void setAnioVencimiento(int anioVencimiento) {
        this.anioVencimiento = anioVencimiento;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


}
