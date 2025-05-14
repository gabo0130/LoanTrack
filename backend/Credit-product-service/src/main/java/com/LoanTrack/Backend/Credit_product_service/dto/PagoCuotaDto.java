package com.LoanTrack.Backend.Credit_product_service.dto;

import com.LoanTrack.Backend.Credit_product_service.entity.Pago;
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
public class PagoCuotaDto {
    private BigDecimal montoPagado;
    private LocalDate fechaPago;

    public PagoCuotaDto(Pago pago) {
        this.montoPagado = pago.getMontoPagado();
        this.fechaPago = pago.getFechaPago();
    }
}
