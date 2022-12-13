package com.gmt.membresias.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*Combrobar si guardar la fecha y hora en variables string o date*/
//Ya que la fecha no se utilizará para hace calculos y sólo es informtiva se guardará en string.
//Pero en la BD sí se guardará como datetime con el formato:
//2021-10-21 09:02:00-07:00
//YYYY-MM-DD hh:mm:ss-00:00
//Las horas son en 24, 

public class DateFormatConfig {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    //private String zona_horaria = "-07:00";
    
    public Date convertir(String fechaString) throws ParseException{
        Date fecha = this.df.parse(fechaString);

        return fecha;
    }

    public String convertir(Date date) throws ParseException{
        String fecha = this.df.format(date);

        return fecha;
    }
}
