package com.gmt.membresias.controllers.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gmt.membresias.exception.ClienteServiceException;
import com.gmt.membresias.exception.EdoMembServiceException;
import com.gmt.membresias.models.Cliente;
import com.gmt.membresias.services.ClienteService;
import com.gmt.membresias.services.EdoMembService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/")
public class EdoMembRestController {

    @Autowired
    private EdoMembService edoMembService;

    @Autowired ClienteService clienteService;

    private String formatString = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);

    @GetMapping("EdoMemb/estado/{idUsuario}")
    public ResponseEntity<Map<String, Object>> EdoMemb (@PathVariable("idUsuario") long idUsuario){
        Map<String, Object> map = new HashMap<String, Object>();
        Cliente usuario = null;
        try {
            usuario = edoMembService.comprobar(idUsuario);
        } catch (EdoMembServiceException e) {
            if(e.getId() == EdoMembServiceException.USER_NOT_FOUND){
                map.put("estado", "Usuario no encontrado");
                map.put("fechaCorte", "00/00/0000 00:00");
                map.put("usuario", null);
                map.put("membresia", null);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }else{
                map.put("estado", "Error interno");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        } catch(ParseException e){
            map.put("estado", "Error interno");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if(usuario.getFechaCorte().before(new Date())){
            map.put("estado", "Membresia vencida");
        }else{
            map.put("estado", "Membresia activa");
        }


        map.put("fechaCorte", simpleDateFormat.format(usuario.getFechaCorte()));
        map.put("usuario", usuario.getId());
        map.put("membresia", usuario.getMembresia());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("UsuarioMemb")
    public ResponseEntity<Map<String, Object>> UsuarioMemb (@RequestBody Cliente cliente){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            clienteService.create(cliente);
        } catch (ClienteServiceException e) {
            map.put("estado", "ERROR");
            map.put("mensaje", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put("estado", "OK");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

// {
//     "id": 1544,
//     "nombre": "Ana",
//     "apellidoPaterno": "De Haro",
//     "apellidoMaterno": "De La Cruz",
//     "correo": "ana.haro@gmail.com"
// }