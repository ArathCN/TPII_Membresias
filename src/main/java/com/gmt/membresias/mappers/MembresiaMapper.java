package com.gmt.membresias.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gmt.membresias.models.Membresia;

public class MembresiaMapper implements RowMapper<Membresia>{

    @Override
    public Membresia mapRow(ResultSet rs, int rowNum) throws SQLException {
        Membresia membresia = new Membresia(
            rs.getByte("id"),
            rs.getString("nombre"),
            rs.getDouble("precio"),
            rs.getInt("duracionDias")
        );
        return membresia;
    }
    
}
