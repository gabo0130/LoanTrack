package com.LoanTrack.Backend.Credit_product_service.entity;

import com.LoanTrack.Backend.Credit_product_service.enumerations.EstadoCredito;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private EstadoCredito estado = EstadoCredito.abierto;


}
