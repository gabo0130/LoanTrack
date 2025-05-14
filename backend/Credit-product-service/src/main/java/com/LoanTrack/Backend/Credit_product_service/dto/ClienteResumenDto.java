package com.LoanTrack.Backend.Credit_product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResumenDto {

    private String tipo;
    private BigDecimal montoTotal;
    private BigDecimal saldoPendiente;
    private String estado;
    private LocalDate fechaAprobacion;
    private ClienteInfoDto infoCliente;
    private List<CreditoUltimoPagoDto> creditos;

    public static class Builder {
        private ClienteInfoDto infoCliente;
        private List<CreditoUltimoPagoDto> creditos;

        public Builder withInfoCliente(ClienteInfoDto infoCliente) {
            this.infoCliente = infoCliente;
            return this;
        }


        public ClienteResumenDto build() {
            ClienteResumenDto dto = new ClienteResumenDto();
            dto.infoCliente = this.infoCliente;
            dto.creditos = this.creditos;
            return dto;
        }
    }

}
