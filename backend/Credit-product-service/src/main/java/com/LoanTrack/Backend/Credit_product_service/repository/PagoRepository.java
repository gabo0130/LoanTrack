package com.LoanTrack.Backend.Credit_product_service.repository;

import com.LoanTrack.Backend.Credit_product_service.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByCuotaId(Integer cuotaId);

    List<Pago> findByCuota_Credito_IdOrderByFechaPagoDesc(Integer creditoId);

    Optional<Pago> findFirstByCuota_Credito_IdOrderByFechaPagoDesc(Integer creditoId);



}
