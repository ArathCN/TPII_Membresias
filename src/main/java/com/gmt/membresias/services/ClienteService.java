package com.gmt.membresias.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmt.membresias.exception.ClienteServiceException;
import com.gmt.membresias.models.Cliente;
import com.gmt.membresias.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void create(Cliente cliente) throws ClienteServiceException{
        try {
            clienteRepository.create(cliente);
        } catch (Exception e) {
            throw new ClienteServiceException(1, "No se pudo registrar al cliente");
        }
    }

    public Cliente readById(long id) throws ClienteServiceException{
        Cliente cliente = null;
        try {
            cliente = clienteRepository.readById(id);
        } catch (Exception e) {
            System.out.println("!!!!" + e.getMessage());
            throw new ClienteServiceException(2, "No se pudo obtener al cliente");
        }
        return cliente;
    }
}
