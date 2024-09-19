package com.transporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterClientRequest implements Serializable {
    @NotBlank
    @Size(min = 1, max = 45)
    private String nombre;
    @NotBlank
    @Size(min = 1, max = 45)
    private String apaterno;
    @NotBlank
    @Size(min = 1, max = 45)
    private String amaterno;
    @NotBlank
    @Size(min = 10, max = 10)
    private String celular;
    @NotNull
    private int edad;
    @Size(min = 1, max = 45)
    private String insen;
    @Size(min = 1, max = 45)
    private String tarjetaId;
}
