package com.gmt.membresias.repositories;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.gmt.membresias.exception.CompraServiceException;
import com.gmt.membresias.models.Compra;

@Repository
public class CompraRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("getDSBean")
    private DataSource dataSource;

    public long create(Compra compra) throws CompraServiceException{
        SimpleJdbcInsert simpleJdbcInsert = 
            new SimpleJdbcInsert(dataSource).withTableName("compra").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("idCliente", compra.getCliente().getId());
        parameters.put("idMembresia", compra.getMembresia().getId());
        parameters.put("fecha", compra.getFecha());
        parameters.put("monto", compra.getMonto());
        parameters.put("tarjeta", compra.getTarjeta());
        parameters.put("transaccion", compra.getTransaccion());
        Number id = null;
        try {
            id = simpleJdbcInsert.executeAndReturnKey(parameters);
        } catch (Exception e) {
            throw new CompraServiceException(1, e.getMessage());
        }
        return id.longValue();
    }
}
