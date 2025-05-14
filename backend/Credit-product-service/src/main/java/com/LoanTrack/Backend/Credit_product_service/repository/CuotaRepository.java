package com.LoanTrack.Backend.Credit_product_service.repository;

import com.LoanTrack.Backend.Credit_product_service.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuotaRepository extends JpaRepository<Cuota, Integer> {


    List<Cuota> findByCreditoId(Integer creditoId);

    @Override
    Optional<Cuota> findById(Integer integer);
}
