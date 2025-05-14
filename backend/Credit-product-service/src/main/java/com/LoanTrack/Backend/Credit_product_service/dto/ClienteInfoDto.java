package com.LoanTrack.Backend.Credit_product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInfoDto {
    private String identificacion;
    private String nombreCompleto;
    private String telefono;
}
