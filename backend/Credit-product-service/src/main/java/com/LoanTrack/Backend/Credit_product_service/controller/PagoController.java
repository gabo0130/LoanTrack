package com.LoanTrack.Backend.Credit_product_service.controller;

import com.LoanTrack.Backend.Credit_product_service.entity.Pago;
import com.LoanTrack.Backend.Credit_product_service.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas/{cuotaId}/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<Pago> registrarPago(
            @PathVariable Integer cuotaId,
            @RequestBody Pago pagoRequest) {
        return ResponseEntity.ok(pagoService.registrarPago(cuotaId, pagoRequest));
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listarPagosDeCuota(
            @PathVariable Integer cuotaId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorCuota(cuotaId));
    }

    @GetMapping("/{pagoId}")
    public ResponseEntity<Pago> obtenerPagoDetalle(
            @PathVariable Integer cuotaId,
            @PathVariable Integer pagoId) {
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(pagoId));
    }

    @PutMapping("/{pagoId}")
    public ResponseEntity<Pago> actualizarPago(
            @PathVariable Integer cuotaId,
            @PathVariable Integer pagoId,
            @RequestBody Pago pagoActualizado) {
        return ResponseEntity.ok(pagoService.actualizarPago(pagoId, pagoActualizado));
    }

    @DeleteMapping("/{pagoId}")
    public ResponseEntity<Void> eliminarPago(
            @PathVariable Integer cuotaId,
            @PathVariable Integer pagoId) {
        pagoService.eliminarPago(pagoId);
        return ResponseEntity.noContent().build();
    }
}