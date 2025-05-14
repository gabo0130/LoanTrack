package com.LoanTrack.Backend.Credit_product_service.service;

import com.LoanTrack.Backend.Credit_product_service.dto.*;
import com.LoanTrack.Backend.Credit_product_service.exceptions.*;
import com.LoanTrack.Backend.Credit_product_service.entity.*;
import com.LoanTrack.Backend.Credit_product_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private PagoRepository pagoRepository;



    /**
     * Verifica si un cliente existe por su identificación
     */
    @Transactional(readOnly = true)
    public boolean existeCliente(String identificacion) {
        return clienteRepository.existsByCedula(identificacion);
    }

    /**
     * Obtiene los créditos de un cliente por identificación
     */
    @Transactional(readOnly = true)
    public List<ClienteResumenDto> obtenerCreditosPorIdentificacion(String identificacion) {
        if (!existeCliente(identificacion)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        ClienteInfoDto infoCliente = this.obtenerInformacionCliente(identificacion);

        return creditoRepository.findByClienteCedula(identificacion)
                .stream()
                .map(credito -> {
                    ClienteResumenDto dto = convertirACreditoDto(credito);
                    dto.setInfoCliente(infoCliente);  // Aquí asignas la info del cliente al DTO
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResumenDto obtenerResumenCliente(String identificacion) {
        // 1. Obtener información básica del cliente
        ClienteInfoDto infoCliente = this.obtenerInformacionCliente(identificacion);

        // 2. Obtener todos los créditos del cliente
        List<CreditoUltimoPagoDto> creditos = creditoRepository
                .findByClienteCedula(identificacion)
                .stream()
                .map(this::convertirACreditoConUltimoPagoDto)
                .collect(Collectors.toList());

        // 3. Construir el DTO de respuesta
        ClienteResumenDto resumen = new ClienteResumenDto();
        resumen.setInfoCliente(infoCliente);
        resumen.setCreditos(creditos);

        return resumen;
    }

    private PagoDto convertirAPagoDto(Pago pago) {
        PagoDto dto = new PagoDto();
        dto.setId(pago.getId());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMonto(pago.getMontoPagado());
        dto.setMetodoPago(pago.getMetodoPago().toString());

        return dto;

}

    private CreditoUltimoPagoDto convertirACreditoConUltimoPagoDto(Credito credito) {
        CreditoUltimoPagoDto dto = new CreditoUltimoPagoDto();

        // Copiar datos básicos del crédito

        dto.setTipo(credito.getTipoCredito().toString());
        dto.setMontoTotal(credito.getMontoTotal());
        dto.setEstado(credito.getEstado().toString());
        dto.setFechaAprobacion(credito.getFechaInicio());

        // Obtener el último pago (si existe)
        pagoRepository.findFirstByCuota_Credito_IdOrderByFechaPagoDesc(credito.getId())
                .ifPresent(pago -> dto.setUltimoPago(convertirAPagoDto(pago)));

        return dto;
    }

    /**
     * Obtiene información básica del cliente
     */
    @Transactional(readOnly = true)
    public ClienteInfoDto obtenerInformacionCliente(String identificacion) {
        Cliente cliente = clienteRepository.findByCedula(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        return convertirAClienteInfoDto(cliente);
    }

    // Métodos de conversión privados
    private ClienteResumenDto convertirACreditoDto(Credito credito) {
        ClienteResumenDto dto = new ClienteResumenDto();
        dto.setTipo(credito.getTipoCredito().toString());
        dto.setMontoTotal(credito.getMontoTotal());
        dto.setEstado(credito.getEstado().toString());
        dto.setFechaAprobacion(credito.getFechaInicio());
        return dto;
    }

    private ClienteInfoDto convertirAClienteInfoDto(Cliente cliente) {
        ClienteInfoDto dto = new ClienteInfoDto();
        dto.setIdentificacion(cliente.getCedula());
        dto.setNombreCompleto(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }
}