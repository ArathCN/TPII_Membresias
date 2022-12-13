package com.gmt.membresias.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.gmt.membresias.components.DateFormatConfig;
import com.gmt.membresias.models.Cliente;
import com.gmt.membresias.models.Membresia;

public class ClienteMapper implements RowMapper<Cliente>{
    
    private DateFormatConfig dateFormatConfig = new DateFormatConfig();

    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Membresia membresia = new Membresia();
        Cliente cliente = null;
        membresia.setId(rs.getLong("idMembresia"));
        Date fechaCorte = null;
        try {
            if(rs.getString("fechaCorte") != null)
                fechaCorte = dateFormatConfig.convertir(rs.getString("fechaCorte"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cliente = new Cliente(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("apellidoPaterno"),
                rs.getString("apellidoMaterno"),
                rs.getString("correo"),
                fechaCorte,
                membresia);
        return cliente;
    }
    
}
