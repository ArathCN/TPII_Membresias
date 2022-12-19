$(document).ready(function(){
    $(".btnMembresia").on("click", function(e){
        let idMembresia = $(this).data("id");
        let idCliente = $("#clienteID").data("idcliente");

        console.log(idMembresia + " ---> " + idCliente);

        let targetURL = "/metodopago?idCliente=" + idCliente + "&idMembresia=" + idMembresia;
        let newURL = document.createElement('a');
        newURL.href = targetURL;
        document.body.appendChild(newURL);
        newURL.click();
    });
});