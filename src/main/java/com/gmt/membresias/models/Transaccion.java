package com.gmt.membresias.models;

public class Transaccion {
    private long iD_Movimiento;
    private String notarjeta_org;
    private String notarjeta_Dst;
    private Double monto;

    public Transaccion(){

    }
    
    public Transaccion(long iD_Movimiento, String notarjeta_org, String notarjeta_Dst, Double monto) {
        this.iD_Movimiento = iD_Movimiento;
        this.notarjeta_org = notarjeta_org;
        this.notarjeta_Dst = notarjeta_Dst;
        this.monto = monto;
    }

    public long getiD_Movimiento() {
        return iD_Movimiento;
    }

    public void setiD_Movimiento(long iD_Movimiento) {
        this.iD_Movimiento = iD_Movimiento;
    }

    public String getNotarjeta_org() {
        return notarjeta_org;
    }

    public void setNotarjeta_org(String notarjeta_org) {
        this.notarjeta_org = notarjeta_org;
    }

    public String getNotarjeta_Dst() {
        return notarjeta_Dst;
    }

    public void setNotarjeta_Dst(String notarjeta_Dst) {
        this.notarjeta_Dst = notarjeta_Dst;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    
}
