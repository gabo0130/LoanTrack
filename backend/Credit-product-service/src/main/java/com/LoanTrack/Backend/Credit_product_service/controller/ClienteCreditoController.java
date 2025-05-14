package com.LoanTrack.Backend.Credit_product_service.controller;

import com.LoanTrack.Backend.Credit_product_service.dto.*;
import com.LoanTrack.Backend.Credit_product_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteCreditoController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CreditoService creditoService;

    @Autowired
    private PagoService pagoService;



    /**
     * Endpoint para obtener los créditos de un cliente por identificación
     * @param identificacion Número de cédula o documento del cliente
     * @return Lista de créditos del cliente
     */
    @GetMapping("/{identificacion}/creditos")
    public ResponseEntity<List<ClienteResumenDto>> getCreditosPorCliente(
            @PathVariable String identificacion) {

        // Validar formato de identificación (ejemplo básico)
        if (identificacion == null || identificacion.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<ClienteResumenDto> creditos = clienteService.obtenerCreditosPorIdentificacion(identificacion);
        return ResponseEntity.ok(creditos);
    }

    /**
     * Endpoint para obtener el historial de pagos de un crédito específico
     * @param identificacion Número de cédula del cliente
     * @param numeroCredito Número visible del crédito (no el ID interno)
     * @return Lista de pagos ordenados por fecha descendente
     * es solo si se agregaa un numero de credito de lo contrario es solo agregarlo con el nombre
     */
    @GetMapping("/{identificacion}/creditos/{numeroCredito}/pagos")
    public ResponseEntity<List<PagoDto>> getHistorialPagos(
            @PathVariable String identificacion,
            @PathVariable String numeroCredito) {

        // Validaciones básicas
        if (identificacion == null || identificacion.trim().isEmpty() ||
                numeroCredito == null || numeroCredito.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<PagoDto> pagos = pagoService.obtenerPagosPorCreditoCliente(identificacion, numeroCredito);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Endpoint adicional: Resumen completo con créditos y último pago
     * @param identificacion Número de cédula del cliente
     * @return Resumen con lista de créditos y último pago de cada uno
     */
    @GetMapping("/{identificacion}/resumen")
    public ResponseEntity<ClienteResumenDto> getResumenCliente(
            @PathVariable String identificacion) {

        ClienteResumenDto resumen = clienteService.obtenerResumenCliente(identificacion);
        return ResponseEntity.ok(resumen);
    }
}
