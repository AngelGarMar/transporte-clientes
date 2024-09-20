package com.transporte.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transporte.entities.Cliente;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterClientResponse {
    private String nombre;
    private String apaterno;
    private String amaterno;
    private String celular;
    private int edad;
    private String insen;
    private String tarjetaId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creado;

    public RegisterClientResponse(Cliente cliente) {
        this.nombre = cliente.getNombre();
        this.apaterno = cliente.getApaterno();
        this.amaterno = cliente.getAmaterno();
        this.celular = cliente.getCelular();
        this.edad = cliente.getEdad();
        this.insen = cliente.getInsen();
        this.tarjetaId = cliente.getTarjetaId();
        this.creado = cliente.getCreado();
    }
}
