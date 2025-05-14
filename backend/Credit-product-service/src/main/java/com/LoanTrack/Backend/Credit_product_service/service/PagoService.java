package com.LoanTrack.Backend.Credit_product_service.service;

import com.LoanTrack.Backend.Credit_product_service.dto.PagoDto;
import com.LoanTrack.Backend.Credit_product_service.entity.Cliente;
import com.LoanTrack.Backend.Credit_product_service.entity.Credito;
import com.LoanTrack.Backend.Credit_product_service.entity.Cuota;
import com.LoanTrack.Backend.Credit_product_service.entity.Pago;
import com.LoanTrack.Backend.Credit_product_service.exceptions.ResourceNotFoundException;
import com.LoanTrack.Backend.Credit_product_service.repository.ClienteRepository;
import com.LoanTrack.Backend.Credit_product_service.repository.CreditoRepository;
import com.LoanTrack.Backend.Credit_product_service.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CuotaService cuotaService;

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private PagoDto convertirADetalleDto(Pago pago) {
        PagoDto dto = new PagoDto();
        dto.setId(pago.getId());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMonto(pago.getMontoPagado());
        dto.setMetodoPago(pago.getMetodoPago().toString());
        return dto;
    }

    public List<PagoDto> obtenerPagosPorCreditoCliente(
            String identificacion, String numeroCredito) {

        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findByCedula(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        // Validar que el crédito pertenece al cliente
        Credito credito = creditoRepository.findByClienteId(cliente.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Crédito no encontrado para este cliente"));

        return pagoRepository.findByCuota_Credito_IdOrderByFechaPagoDesc(credito.getId()).stream()
                .map(this::convertirADetalleDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public Pago registrarPago(Integer cuotaId, Pago pago) {
        Cuota cuota = cuotaService.obtenerCuota(cuotaId);

        // Validar monto
        if(pago.getMontoPagado().compareTo(cuota.getSaldoPendiente()) > 0) {
            throw new RuntimeException("Monto excede el saldo pendiente");
        }

        // Registrar pago
        pago.setCuota(cuota);
        Pago pagoRegistrado = pagoRepository.save(pago);

        // Actualizar cuota
        cuota.registrarPago(pago.getMontoPagado());
        cuotaService.actualizarCuota(cuota, cuota.getMontoCuota());

        return pagoRegistrado;
    }

    public List<Pago> obtenerPagosPorCuota(Integer cuotaId) {
        return pagoRepository.findByCuotaId(cuotaId);
    }

    public Pago obtenerPagoPorId(Integer pagoId) {
        return pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + pagoId));
    }

    public void validarPago(Pago pago) {
        if (pago.getMontoPagado() == null || pago.getMontoPagado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }

        if (pago.getCuota() == null) {
            throw new IllegalArgumentException("El pago debe estar asociado a una cuota.");
        }

        BigDecimal saldoPendiente = pago.getCuota().getSaldoPendiente();
        if (saldoPendiente != null && pago.getMontoPagado().compareTo(saldoPendiente) > 0) {
            throw new IllegalArgumentException("El monto del pago no puede exceder el saldo pendiente.");
        }
    }


    @Transactional
    public Pago actualizarPago(Integer pagoId, Pago pagoActualizado) {
        Pago pagoExistente = obtenerPagoPorId(pagoId);

        // Actualizar campos permitidos
        pagoExistente.setFechaPago(pagoActualizado.getFechaPago());
        pagoExistente.setMontoPagado(pagoActualizado.getMontoPagado());
        pagoExistente.setMetodoPago(pagoActualizado.getMetodoPago());

        // Validar cambios
        validarPago(pagoExistente);

        return pagoRepository.save(pagoExistente);
    }

    public void eliminarPago(Integer id){
        pagoRepository.deleteById(id);
    }


}
