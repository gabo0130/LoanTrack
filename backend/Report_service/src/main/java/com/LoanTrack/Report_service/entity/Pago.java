package com.LoanTrack.Report_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.LoanTrack.Report_service.entity.Cuota;


@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cuota", nullable = false)
    private Cuota cuota;


    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "monto_pagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoPagado;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Cuota getCuota() { return cuota; }

    public void setCuota(Cuota cuota) { this.cuota = cuota; }

    public LocalDate getFechaPago() { return fechaPago; }

    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public BigDecimal getMontoPagado() { return montoPagado; }

    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }

    public String getMetodoPago() { return metodoPago; }

    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
