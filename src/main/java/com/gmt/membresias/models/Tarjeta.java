package com.gmt.membresias.models;

public class Tarjeta {
    private long numero;
    private String vencimiento;
    private int codigo;

    public Tarjeta(){
        
    }
    
    

    public Tarjeta(long numero, String vencimiento, int codigo) {
        this.numero = numero;
        this.vencimiento = vencimiento;
        this.codigo = codigo;
    }



    public long getNumero() {
        return numero;
    }
    public void setNumero(long numero) {
        this.numero = numero;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getVencimiento() {
        return vencimiento;
    }
    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }


}
