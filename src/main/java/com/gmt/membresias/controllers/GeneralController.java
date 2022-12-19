package com.gmt.membresias.controllers;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.gmt.membresias.components.DateFormatConfig;
import com.gmt.membresias.exception.ClienteServiceException;
import com.gmt.membresias.exception.CompraServiceException;
import com.gmt.membresias.models.Cliente;
import com.gmt.membresias.models.Compra;
import com.gmt.membresias.models.Membresia;
import com.gmt.membresias.models.Respuesta;
import com.gmt.membresias.models.Tarjeta;
import com.gmt.membresias.services.ClienteService;
import com.gmt.membresias.services.CompraService;
import com.gmt.membresias.services.MembresiaService;

@Controller
public class GeneralController {

    @Autowired
    private MembresiaService membresiaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CompraService compraService;

    private DateFormatConfig dateFormatConfig = new DateFormatConfig();

    private String banco = "http://www.itbank.somee.com/api/Usuarios/Transferencia/";
    private String cuenta = "2154326554876691";
    
    @GetMapping(path="/membresias")
    @ResponseBody
    public ModelAndView membresias(
        @RequestParam(name = "id", required = true) long id,
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
    public ModelAndView metodopago(
        @RequestParam(name = "idCliente") long idCliente,
        @RequestParam(name = "idMembresia") long idMembresia,
        @RequestParam(name = "estado", defaultValue = "OK") String estado
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

    @PostMapping(path = "/pagooo", consumes = {"application/json"})
    public ResponseEntity<Map<String, Object>> pago(
        @RequestParam("idCliente") long idCliente,
        @RequestParam("idMembresia") long idMembresia,
        @RequestParam("numero") long numero,
        @RequestParam("vencimiento") String vencimiento,
        @RequestParam("codigo") int codigo
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Tarjeta tarjeta = new Tarjeta(numero, vencimiento, codigo);
        Cliente cliente = null;
        Membresia membresia = null;
        try {
            cliente = clienteService.readById(idCliente);
            membresia = membresiaService.readById(idMembresia);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            map.put("estado", "INTERNAL_ERROR");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if(cliente == null){
            map.put("estado", "USER_NOT_FOUND");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        if(membresia == null){
            map.put("estado", "MEMBERSHIP_NOT_FOUND");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        Date fecha = new Date();
        int digitos = Integer.parseInt((tarjeta.getNumero()+"").substring(12));
        RestTemplate restTemplate = new RestTemplate();
        String peticion = "" + tarjeta.getNumero() + "," + vencimiento.replaceAll("/", "%2F") +
            "," + tarjeta.getCodigo() + "," + cuenta + "," + membresia.getPrecio();
        
        map.put("banco", banco + peticion);
        map.put("digitos", digitos);
        Respuesta transaccion = null;
        String error = null;
         HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
         headers.set("accept", "*/*");
        // headers.set("host", "itbank.somee.com");
        // headers.setContentLength(0);
        // headers.setHost(new InetSocketAddress("itbank.somee.com", 0));

        MultiValueMap<String, Object> datos = new LinkedMultiValueMap<>();
        datos.add("Id", 78912);
        datos.add("Quantity", 1);
        datos.add("Price", 18.00);

        HttpEntity<Object> httpEntity = new HttpEntity<>(datos, headers);
        try {
            //banco + peticion, Respuesta.class
            //transaccion =  restTemplate.postForObject((banco + peticion), null, Respuesta.class);
            //error = restTemplate.postForObject("https://reqbin.com/echo/post/json", datos, String.class);
            Object data = restTemplate.postForObject((banco + peticion), datos, Object.class);
            map.put("DATA", data);
        }  catch (HttpClientErrorException ex) {
            map.put("respuesta", ex.getResponseBodyAsString());
            map.put("Code", ex.getRawStatusCode());
            System.out.println();
        } catch (RestClientException e) {
            System.out.println(e.getMessage() + " --> " + e.getCause());
        }
        // if(transaccion != null){
        //     Compra compra = new Compra(idMembresia, fecha, membresia.getPrecio(), digitos, transaccion.getResponse().getiD_Movimiento(), idCliente, idMembresia);
        //     try {
        //         compraService.create(compra);
        //         map.put("estado", "OK");
        //     } catch (CompraServiceException e) {
        //         e.printStackTrace();
        //         return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        //     }
        // }else if(error != null){
        //     map.put("estado", "SALDO_INSUFICIENTE");
        //     return new ResponseEntity<>(map, HttpStatus.OK);
        // }else{
        //     return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        // }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(path = "/pago", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrarCompra(
        @RequestBody Compra compra
    ){
        Map<String, Object> map = new HashMap<String, Object>();
        Cliente cliente = null;
        Membresia membresia = null;
        try {
            cliente = clienteService.readById(compra.getCliente().getId());
            membresia = membresiaService.readById(compra.getMembresia().getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            map.put("estado", "INTERNAL_ERROR");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null){
            map.put("estado", "USER_NOT_FOUND");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        if(membresia == null){
            map.put("estado", "MEMBERSHIP_NOT_FOUND");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        Date fecha = new Date();
        compra.setFecha(fecha);
        try {
            compraService.create(compra);
            map.put("estado", "OK");
        } catch (CompraServiceException e) {
            e.printStackTrace();
            map.put("estado", "REGISTRATION_NOT_COMPLETED");
            map.put("compra", compra);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
