package com.gmt.membresias.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gmt.membresias.exception.ClienteServiceException;
import com.gmt.membresias.models.Cliente;
import com.gmt.membresias.models.Membresia;
import com.gmt.membresias.services.ClienteService;
import com.gmt.membresias.services.MembresiaService;

@Controller
public class GeneralController {

    @Autowired
    private MembresiaService membresiaService;

    @Autowired
    private ClienteService clienteService;
    
    @GetMapping(path="/membresias")
    @ResponseBody
    public ModelAndView membresias(
        @RequestParam(name = "id", required = false, defaultValue = "0") long id,
        Pageable pageable
    ){
        ModelAndView modelAndView = new ModelAndView("index");
        Cliente cliente = null;
        try {
            cliente = clienteService.readById(id);
        } catch (ClienteServiceException e) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(e.getMessage());
            modelAndView.addObject("estado", "USER_NOT_FOUND");
            return modelAndView;
        }

        if(cliente == null){
            modelAndView.addObject("estado", "USER_NOT_FOUND");
            return modelAndView;
        }

        modelAndView.addObject("cliente", cliente);

        List<Membresia> membresias = null;
        try {
            membresias = membresiaService.readAll(pageable);
            modelAndView.addObject("membresias", membresias);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(path = "/metodopago")
    public ModelAndView pago(
        @RequestParam(name = "idCliente") long idCliente,
        @RequestParam(name = "idMembresia") long idMembresia
    ){
        ModelAndView modelAndView = new ModelAndView("metodopago");
        Cliente cliente = null;
        Membresia membresia = null;
        try {
            cliente = clienteService.readById(idCliente);
            membresia = membresiaService.readById(idMembresia);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelAndView.setViewName("redirect:/membresias?id=2");
        }

        if(cliente == null || membresia == null){
            modelAndView.setViewName("redirect:/membresias?id=2");
        }

        modelAndView.addObject("membresia", membresia);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

}
