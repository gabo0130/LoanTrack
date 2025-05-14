package com.LoanTrack.Backend.Credit_product_service.service;

import com.LoanTrack.Backend.Credit_product_service.dto.CuotaDto;
import com.LoanTrack.Backend.Credit_product_service.entity.Credito;
import com.LoanTrack.Backend.Credit_product_service.entity.Cuota;
import com.LoanTrack.Backend.Credit_product_service.entity.Pago;
import com.LoanTrack.Backend.Credit_product_service.enumerations.EstadoCredito;
import com.LoanTrack.Backend.Credit_product_service.repository.CreditoRepository;
import com.LoanTrack.Backend.Credit_product_service.repository.CuotaRepository;
import com.LoanTrack.Backend.Credit_product_service.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CreditoRepository creditoRepository;

    @Transactional
    public List<Cuota> generarCuotas(Credito credito) {
        List<Cuota> cuotas = new ArrayList<>();
        BigDecimal montoCuota = credito.getMontoTotal()
                .divide(BigDecimal.valueOf(credito.getPlazoCuotas()), 2, RoundingMode.HALF_UP);

        for (int i = 1; i <= credito.getPlazoCuotas(); i++) {
            Cuota cuota = Cuota.builder()
                    .credito(credito)
                    .numeroCuota(i)
                    .montoCuota(montoCuota)
                    .fechaVencimiento(credito.getFechaInicio().plusMonths(i))
                    .estado(EstadoCredito.moroso)
                    .saldoPendiente(montoCuota)
                    .build();
            cuotas.add(cuotaRepository.save(cuota));
        }
        return cuotas;
    }

  //  public List<Cuota> obtenerCuotasPorCredito(Integer creditoId) {

    //    return cuotaRepository.findByCreditoId(creditoId);
   // }

    public List<CuotaDto> obtenerCuotasPorCredito(Integer creditoId) {
        List<Cuota> listaDeCuotas = cuotaRepository.findByCreditoId(creditoId);

        // Forzar inicialización de pagos
        listaDeCuotas.forEach(cuota -> cuota.getPagos().size());

        List<CuotaDto> dtoList = listaDeCuotas.stream()
                .map(cuota -> new CuotaDto(cuota)) // aquí usas tu constructor
                .collect(Collectors.toList());

        return dtoList;
    }





    public Cuota obtenerCuota(Integer id) {
        return cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));
    }

    @Transactional
    public void actualizarCuota(Cuota cuota, BigDecimal montoPagado) {
        // Calcular nuevo saldo
        BigDecimal nuevoSaldo = cuota.getSaldoPendiente().subtract(montoPagado);
        cuota.setSaldoPendiente(nuevoSaldo);

        // Actualizar estado según el saldo
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) <= 0) {
            cuota.setEstado(EstadoCredito.cancelado);
        } else if (cuota.getEstado() == EstadoCredito.moroso) {
            cuota.setEstado(EstadoCredito.parcial);
        }

        // Verificar vencimiento
        if (LocalDate.now().isAfter(cuota.getFechaVencimiento()) &&
                cuota.getEstado() != EstadoCredito.cancelado) {
            cuota.setEstado(EstadoCredito.vencida);
        }

        cuotaRepository.save(cuota);
    }

    @Transactional
    public Cuota actualizarEstadoCuota(Integer cuotaId, String nuevoEstado) {
        // Validar parámetros de entrada
        if (cuotaId == null || cuotaId <= 0) {
            throw new IllegalArgumentException("ID de cuota no válido");
        }
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }

        Cuota cuota = obtenerCuotaPorId(cuotaId);

        try {
            EstadoCredito estado = EstadoCredito.valueOf(nuevoEstado.toLowerCase());

            // Validar transición de estado
            if (cuota.getEstado() == EstadoCredito.pagada && estado != EstadoCredito.pagada) {
                throw new IllegalStateException("No se puede modificar una cuota ya pagada");
            }

            cuota.setEstado(estado);
            return cuotaRepository.save(cuota);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado no válido. Valores permitidos: "
                    + Arrays.toString(EstadoCredito.values()));
        }
    }

    // Versión CORRECTA que devuelve Cuota
    public Cuota obtenerCuotaPorId(Integer cuotaId) {
        return cuotaRepository.findById(cuotaId)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada con ID: " + cuotaId));
    }

    public List<Pago> obtenerPagosDeCuota(Integer cuotaId) {
        // Implementación con el repositorio de pagos
        return pagoRepository.findByCuotaId(cuotaId);
        // O alternativa con la relación bidireccional:
        // return obtenerCuotaPorId(cuotaId).getPagos();
    }


}
