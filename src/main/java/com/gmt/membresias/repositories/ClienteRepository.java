package com.gmt.membresias.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.gmt.membresias.exception.ClienteRepositoryException;
import com.gmt.membresias.mappers.ClienteMapper;
import com.gmt.membresias.models.Cliente;

@Repository
public class ClienteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("getDSBean")
    private DataSource dataSource;

    public long create(Cliente cliente) throws ClienteRepositoryException{
        SimpleJdbcInsert simpleJdbcInsert = 
            new SimpleJdbcInsert(dataSource).withTableName("cliente").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("nombre", cliente.getNombre());
        parameters.put("apellidoPaterno", cliente.getApellidoPaterno());
        parameters.put("apellidoMaterno", cliente.getApellidoMaterno());
        parameters.put("correo", cliente.getCorreo());
        Number id = null;
        try {
            id = simpleJdbcInsert.executeAndReturnKey(parameters);
        } catch (Exception e) {
            throw new ClienteRepositoryException(1, e.getMessage());
        }
        return id.longValue();
    }

    public Cliente readById(long id) throws Exception{
        String sql = "SELECT * FROM cliente WHERE id = ?;";

        Object[] params = new Object[] {id};

        List<Cliente> clientes = null;
        try {
            clientes = jdbcTemplate.query(sql, params, new int[] {java.sql.Types.INTEGER}, new ClienteMapper());
        } catch (Exception e) {
            System.out.println("++" + e.getMessage());
            e.printStackTrace();
            clientes = new ArrayList<>();
        }
        if(clientes.size() == 1) return clientes.get(0);
        return null;
    }

}
