package com.LoanTrack.Backend.Payment_service.service;

import com.LoanTrack.Backend.Payment_service.entity.Cuota;
import com.LoanTrack.Backend.Payment_service.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuotaServiceImpl implements CuotaService {

    private final CuotaRepository cuotaRepository;

    @Autowired
    public CuotaServiceImpl(CuotaRepository cuotaRepository) {
        this.cuotaRepository = cuotaRepository;
    }

    @Override
    public List<Cuota> getAll() {
        return cuotaRepository.findAll();
    }

    @Override
    public Optional<Cuota> getById(Long id) {
        return cuotaRepository.findById(id);
    }

    @Override
    public Cuota create(Cuota cuota) {
        return cuotaRepository.save(cuota);
    }

    @Override
    public Cuota update(Long id, Cuota data) {
        return cuotaRepository.findById(id).map(existing -> {
            existing.setNumeroCuota(data.getNumeroCuota());
            existing.setMontoCuota(data.getMontoCuota());
            existing.setFechaVencimiento(data.getFechaVencimiento());
            existing.setEstado(data.getEstado());
            existing.setSaldoPendiente(data.getSaldoPendiente());
            return cuotaRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Cuota no encontrada con ID: " + id));
    }

}