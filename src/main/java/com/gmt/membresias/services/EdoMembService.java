package com.gmt.membresias.services;

import com.gmt.membresias.exception.EdoMembServiceException;
import com.gmt.membresias.models.Cliente;
import com.gmt.membresias.models.Membresia;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class EdoMembService {
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);

    public Cliente comprobar (long idUsuario) throws EdoMembServiceException, ParseException{
        Membresia membresia1 = new Membresia(
            1,
            "Membresia básica 1 mes",
            150.00,
            30
        );

        Membresia membresia2 = new Membresia(
            1,
            "Membresia básica 6 meses",
            600.00,
            60
        );

        Cliente usuario1 = new Cliente();
        usuario1.setId(idUsuario);
        usuario1.setNombre("Ana Sofía");
        usuario1.setApellidoPaterno("De Haro");
        usuario1.setApellidoMaterno("De La Cruz");

        if(idUsuario <= 0){
            throw new EdoMembServiceException(EdoMembServiceException.USER_NOT_FOUND, "Usuario no encontrado");
        }else if (idUsuario > 0 && idUsuario < 10){
            usuario1.setMembresia(membresia1);
            usuario1.setFechaCorte(dateFormat.parse("01/05/2023"));
            return usuario1;
        }else if (idUsuario >= 10 && idUsuario < 100){
            usuario1.setMembresia(membresia2);
            usuario1.setFechaCorte(dateFormat.parse("12/29/2022"));
            return usuario1;
        }else{
            usuario1.setMembresia(membresia1);
            usuario1.setFechaCorte(dateFormat.parse("06/13/2022"));
            return usuario1;
        }
    } 
}