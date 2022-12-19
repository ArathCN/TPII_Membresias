package com.gmt.membresias.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmt.membresias.exception.CompraServiceException;
import com.gmt.membresias.models.Compra;
import com.gmt.membresias.repositories.CompraRepository;

@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;

    public void create(Compra compra) throws CompraServiceException{
        try {
            compraRepository.create(compra);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CompraServiceException(1, "No se pudo registrar la compra");
        }
    }
}
