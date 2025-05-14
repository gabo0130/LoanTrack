package com.LoanTrack.Backend.Credit_product_service.controller;

import com.LoanTrack.Backend.Credit_product_service.dto.CuotaDto;
import com.LoanTrack.Backend.Credit_product_service.entity.Cuota;
import com.LoanTrack.Backend.Credit_product_service.entity.Pago;
import com.LoanTrack.Backend.Credit_product_service.service.CuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/creditos")
@RequiredArgsConstructor
public class CuotaController {

    private final CuotaService cuotaService;

    // Endpoint para listar cuotas de un crédito
    @GetMapping("/{creditoId}/cuotas")
    public ResponseEntity<List<CuotaDto>> listarCuotasPorCredito(
            @PathVariable Integer creditoId) {
        return ResponseEntity.ok(cuotaService.obtenerCuotasPorCredito(creditoId));
    }

    // Endpoint específico para marcar como pagada
    @PatchMapping("/{creditoId}/cuotas/{cuotaId}/pagada")
    public ResponseEntity<Cuota> marcarComoPagada(
            @PathVariable Integer creditoId,
            @PathVariable Integer cuotaId) {
        return ResponseEntity.ok(cuotaService.actualizarEstadoCuota(cuotaId, "pagada"));
    }

    // Endpoint genérico para cambiar estado (opcional)
    @PatchMapping("/{creditoId}/cuotas/{cuotaId}/estado")
    public ResponseEntity<Cuota> actualizarEstadoCuota(
            @PathVariable Integer creditoId,
            @PathVariable Integer cuotaId,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(cuotaService.actualizarEstadoCuota(cuotaId, nuevoEstado));
    }

    // Endpoint para listar pagos
    @GetMapping("/{creditoId}/cuotas/{cuotaId}/pagos")
    public ResponseEntity<List<Pago>> listarPagosDeCuota(
            @PathVariable Integer creditoId,
            @PathVariable Integer cuotaId) {
        return ResponseEntity.ok(cuotaService.obtenerPagosDeCuota(cuotaId));
    }
}