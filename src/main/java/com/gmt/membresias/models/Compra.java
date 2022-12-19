package com.gmt.membresias.models;

import java.util.Date;

public class Compra {
    private long id;
    private Date fecha;
    private double monto;
    private int tarjeta;
    private long transaccion;
    private Cliente cliente;
    private Membresia membresia;

    
    public Compra(){

    }
    public Compra(long id, Date fecha, double monto, int tarjeta, long transaccion, Cliente cliente,
            Membresia membresia) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.tarjeta = tarjeta;
        this.transaccion = transaccion;
        this.cliente = cliente;
        this.membresia = membresia;
    }
    public Compra(long id, Date fecha, double monto, int tarjeta, long transaccion, long cliente,
            long membresia) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.tarjeta = tarjeta;
        this.transaccion = transaccion;
        this.cliente = new Cliente();
        this.cliente.setId(cliente);
        this.membresia = new Membresia();
        this.membresia.setId(membresia);
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public int getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }
    public long getTransaccion() {
        return transaccion;
    }
    public void setTransaccion(long transaccion) {
        this.transaccion = transaccion;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Membresia getMembresia() {
        return membresia;
    }
    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }
    
}
