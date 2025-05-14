package com.LoanTrack.Backend.Credit_product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDto {
    private Integer id;
    private LocalDate fechaPago;
    private BigDecimal monto;
    private String metodoPago;
  //  private String estado;
   // private String referencia;
}
