package com.gmt.membresias.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmt.membresias.models.Membresia;
import com.gmt.membresias.repositories.MembresiaRepository;

@Service
public class MembresiaService {
    @Autowired
    private MembresiaRepository membresiaRepository;

    public List<Membresia> readAll(Pageable pageable) throws Exception{
        return  membresiaRepository.readAll(pageable);
    }

    public Membresia readById(long id) throws Exception{
        return membresiaRepository.readById(id);
    }
}
