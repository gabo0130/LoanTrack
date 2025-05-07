package com.LoanTrack.Backend.Customer_service.repository;


import com.LoanTrack.Backend.Customer_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
}
