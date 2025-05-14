package com.LoanTrack.Backend.Credit_product_service.entity;
import com.LoanTrack.Backend.Credit_product_service.enumerations.MetodoPago;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_cuota",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_pago_cuota")
    )
    @JsonBackReference // Evita recursión infinita en JSON
    private Cuota cuota;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "monto_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private MetodoPago metodoPago;

    // Método de negocio para validar pago
    public boolean esPagoValido() {
        return montoPagado.compareTo(BigDecimal.ZERO) > 0 &&
                fechaPago != null &&
                metodoPago != null;
    }


}