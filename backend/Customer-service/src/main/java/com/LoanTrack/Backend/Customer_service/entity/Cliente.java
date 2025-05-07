package com.LoanTrack.Backend.Customer_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String cedula;

    private String direccion;
    private String telefono;

    @Column(name = "es_nuevo")
    private Boolean esNuevo = true;

    @Column(name = "tope_credito", precision = 12, scale = 2)
    private BigDecimal topeCredito = new BigDecimal("250000.00");

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.activo;

    public enum Estado {
        activo, inactivo, suspendido
    }
}
