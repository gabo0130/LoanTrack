package com.LoanTrack.Report_service.repository;


import com.LoanTrack.Report_service.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {


    List<Pago> findByFechaPagoBetween(LocalDate fechaInicio, LocalDate fechaFin);

}
