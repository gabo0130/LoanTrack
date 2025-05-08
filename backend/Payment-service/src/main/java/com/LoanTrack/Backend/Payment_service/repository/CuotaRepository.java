package com.LoanTrack.Backend.Payment_service.repository;


import com.LoanTrack.Backend.Payment_service.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {
}
