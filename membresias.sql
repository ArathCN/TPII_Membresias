CREATE DATABASE membresias;
USE membresias;

CREATE TABLE `cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) NOT NULL,
  `apellidoPaterno` varchar(60) NOT NULL,
  `apellidoMaterno` varchar(60) NOT NULL,
  `correo` varchar(60) NOT NULL,
  `idMembresia` INT DEFAULT NULL,
  `fechaCorte` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
  );
  
  CREATE TABLE `membresia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `duracionDias` INT NOT NULL,
  `precio` DOUBLE NOT NULL,
  PRIMARY KEY (`id`)
  );
  
    CREATE TABLE `compra` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idCliente` INT NOT NULL,
  `idMembresia` INT NOT NULL,
  `fecha` DATETIME NOT NULL,
  `monto` DOUBLE NOT NULL,
  `tarjeta` INT NOT NULL,
  `transaccion` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FK_compra_cliente_id FOREIGN KEY (`idCliente`) REFERENCES cliente(`id`),
  CONSTRAINT FK_compra_membresia_id FOREIGN KEY (`idMembresia`) REFERENCES membresia(`id`)
  );
  
