package com.LoanTrack.Backend.Credit_product_service.dto;

import com.LoanTrack.Backend.Credit_product_service.entity.Cuota;
import com.LoanTrack.Backend.Credit_product_service.enumerations.EstadoCredito;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuotaDto {

    private Integer id;
    private Integer numeroCuota;
    private BigDecimal montoCuota;
    private LocalDate fechaVencimiento;
    private EstadoCredito estado;
    private BigDecimal saldoPendiente;
    private List<PagoCuotaDto> pagos;


    public CuotaDto(Cuota cuota) {
        this.id = cuota.getId();
        this.numeroCuota = cuota.getNumeroCuota();
        this.montoCuota = cuota.getMontoCuota();
        this.fechaVencimiento = cuota.getFechaVencimiento();
        this.estado = cuota.getEstado();
        this.saldoPendiente = cuota.getSaldoPendiente();

        if (cuota.getPagos() != null) {
            this.pagos = cuota.getPagos().stream()
                    .map(PagoCuotaDto::new)
                    .collect(Collectors.toList());
        }

    }
}
