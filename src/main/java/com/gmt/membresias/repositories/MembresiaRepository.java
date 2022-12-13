package com.gmt.membresias.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.gmt.membresias.mappers.MembresiaMapper;
import com.gmt.membresias.models.Membresia;

@Repository
public class MembresiaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("getDSBean")
    private DataSource dataSource;

    public long create(Membresia membresia){
        SimpleJdbcInsert simpleJdbcInsert = 
            new SimpleJdbcInsert(dataSource).withTableName("membresia").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("nombre", membresia.getDescripcion());
        parameters.put("duracionDias", membresia.getDuracion());
        parameters.put("precio", membresia.getPrecio());
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        if(id == null) return -1;
        return id.longValue();
    }

    public List<Membresia> readAll(Pageable page){
        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("id");
        
        String sqlString = "SELECT * FROM membresia ORDER BY " + order.getProperty() + " "
        + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset();
        
        List<Membresia> categorias = jdbcTemplate.query(sqlString, new MembresiaMapper());
        return categorias;
    }

    public Membresia readById(long id){
        String sql = "SELECT * FROM membresia WHERE id = ?;";

        Object[] params = new Object[] {id};

        List<Membresia> _docente = jdbcTemplate.query(sql, params, new int[] {java.sql.Types.INTEGER}, new MembresiaMapper());
        
        if(_docente.size() == 1) return _docente.get(0);
        return null;
    }
}
