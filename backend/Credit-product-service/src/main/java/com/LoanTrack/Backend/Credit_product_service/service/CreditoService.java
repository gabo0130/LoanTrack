package com.LoanTrack.Backend.Credit_product_service.service;

import com.LoanTrack.Backend.Credit_product_service.entity.Credito;
import com.LoanTrack.Backend.Credit_product_service.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoService {


    @Autowired
    private CreditoRepository creditoRepository;


    public Page<Credito> getCreditosWithPagination(String search, int offset, int limit) {
        PageRequest pageable = PageRequest.of(offset, limit);
        return creditoRepository.findByClienteNombreContainingIgnoreCase(search, pageable);
    }

    public List<Credito> findByNombre(String nombre){
       return creditoRepository.findByClienteNombreContainingIgnoreCase(nombre);
    }

    public Optional<Credito> getCreditoWithDetails(Integer id) {
        // Puedes usar .findById con joins si defines `@EntityGraph` o query personalizada
        return creditoRepository.findById(id);
    }

    public Credito getCreditoById(Integer id) {
        return creditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crédito no encontrado con id: " + id));
    }


    public Credito createCredito(Credito credito) {
        return creditoRepository.save(credito);
    }

    public Credito updateCredito(Integer id, Credito credito) {
        credito.setId(id);
        return creditoRepository.save(credito);
    }
}
