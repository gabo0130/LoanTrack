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
public class CreditoUltimoPagoDto {
 //   private String numeroCredito;
    private String tipo;
    private BigDecimal montoTotal;
    private BigDecimal saldoPendiente;
    private String estado;
    private LocalDate fechaAprobacion;
    private PagoDto ultimoPago;
}
