package com.LoanTrack.Backend.Payment_service.service;


import com.LoanTrack.Backend.Payment_service.entity.Cuota;

import java.util.List;
import java.util.Optional;

public interface CuotaService {
    List<Cuota> getAll();

    Optional<Cuota> getById(Long id);

    List<Cuota> getAllCuotas();

    List<Cuota> getAllCuotasByCreditoId(Long creditoId);
    Optional<Cuota> getCuotaById(Long id);
    Cuota createCuota(Cuota cuota);
    Cuota updateCuota(Long id, Cuota cuota);
    void deleteCuota(Long id);

    Cuota create(Cuota cuota);

    Cuota update(Long id, Cuota data);
}