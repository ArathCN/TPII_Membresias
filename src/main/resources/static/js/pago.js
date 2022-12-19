$(document).ready(function(){

    $("#confirmarbtn").on("click", function(e){
        e.preventDefault();
        $("#confirmarbtn").prop("disabled", true);
        $("#modal_container_procesando").show();

        const CUENTA = "5481791846742980";
        const PAGINA_BANCO = "http://www.itbank.somee.com/api/Usuarios/Transferencia/";
        const PAGINA_MEMBRESIAS = "http://localhost:8080/pago/";
        const PAGINA_VENTAS = "https://us-central1-api-firebase-6b9e5.cloudfunctions.net/app/api/usuarios/";
        let monto = $("#importeTotal").data("importe");

        let formData = new FormData(document.getElementById("pagoForm"));

        if(formData.get("numero") == "" || formData.get("vencimiento") == "" || formData.get("codigo") == ""){
            $("#modal_container_rechazado .mensaje").text("Debe de llenar el formulario");
            $("#modal_container_procesando").hide();
            $("#modal_container_rechazado").show();
            $("#confirmarbtn").prop("disabled", false);
            return;
        }

        let peticion = formData.get("numero") + "," + formData.get("vencimiento").replace("/", "%2F") + "," + formData.get("codigo") + "," + CUENTA + "," + monto;

        console.log(PAGINA_BANCO + peticion);
        let respuesta = llamada(PAGINA_BANCO + peticion, null, "POST", "json");
        console.log(respuesta);
        
        if(respuesta.error != null && respuesta.error !== undefined){
            $("#modal_container_rechazado .mensaje").text("El pago ha sido rechazado: " + respuesta.responseText);
            $("#modal_container_procesando").hide();
            $("#modal_container_rechazado").show();
            $("#confirmarbtn").prop("disabled", false);
            return;
        }

        let datosPago = {
            "transaccion": respuesta.response.iD_Movimiento.toString(),
            "tarjeta": formData.get("numero").slice(-4),
            "monto": respuesta.response.monto.toString(),
            "cliente": {
                "id": "" + formData.get("idCliente")
            },
            "membresia": {
                "id": "" + formData.get("idMembresia")
            }
        }
        console.log(datosPago);

        let respuestaRegistro = llamada(PAGINA_MEMBRESIAS, JSON.stringify(datosPago), "POST", "json");
        console.log(respuestaRegistro);

        if(respuestaRegistro.error != null && respuestaRegistro.error !== undefined){
            $("#modal_container_rechazado .mensaje").text("El pago ha podido ser registrado: \n" + respuestaRegistro.responseJson.estado);
            $("#modal_container_procesando").hide();
            $("#modal_container_rechazado").show();
            $("#confirmarbtn").prop("disabled", false);
            return;
        }

        let datosVentas = {
            "id": "1",
            "fecha_corte": "18/12/2022",
            "estado_membresia": "true"
        }

        let respuestaVentas = llamada(PAGINA_VENTAS + "1", JSON.stringify(datosVentas), "PUT", "json");
        console.log(respuestaVentas);
        //respuestaVentas.error = null;

        // if(respuestaVentas.error != null && respuestaVentas.error !== undefined){
        //     $("#modal_container_rechazado .mensaje").text("El pago ha podido ser registrado: \n" + respuestaVentas.error);
        //     $("#modal_container_procesando").hide();
        //     $("#modal_container_rechazado").show();
        //     $("#confirmarbtn").prop("disabled", false);
        //     return;
        // }

        $("#modal_container_aceptado").show();
        $("#modal_container_procesando").hide();
        $("#confirmarbtn").prop("disabled", false);
    });

    $("button.tiendabtn_rechazado").on("click", function(e){
        $("#modal_container_rechazado").hide();
        console.log("a");
    });

    $("button#tiendabtn_aceptado").on("click", function(e){
        let targetURL = 'https://www.delftstack.com';
        let newURL = document.createElement('a');
        newURL.href = targetURL;
        document.body.appendChild(newURL);
        newURL.click();
    });

    function llamada (url, data, metodo, tipo){
        let respuesta = new Object();
        $.ajax({
            data: data,
            method: metodo,
            url: url,
            cache: false,
            async: false,
            dataType: tipo,
            contentType: 'application/json',
            processData: false,
            success: function (data){
                respuesta = data;
            },
            error: function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR);
                console.log("The following error occured: "+ textStatus +" "+ errorThrown);
                respuesta.error = errorThrown;
                respuesta.responseText = jqXHR.responseText;
                respuesta.responseJson = jqXHR.responseJSON;

            }
        });
        return respuesta;
    }
});