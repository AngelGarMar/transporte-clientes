package com.transporte.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Size(min = 1, max = 45)
    private String credencial;
    @NotNull
    private int edad;
    @Size(min = 1, max = 45)
    private String insen;
    @NotBlank
    @Size(min = 10, max = 10)
    private String celular;
    @Size(min = 1, max = 20)
    private String tarjetaId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creado;
    @Column(name = "estatus")
    private int estatus;
}
