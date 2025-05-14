package com.LoanTrack.Backend.Credit_product_service.repository;

import com.LoanTrack.Backend.Credit_product_service.entity.Credito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CreditoRepository extends JpaRepository<Credito, Integer> {

    @Query("SELECT c FROM Credito c JOIN c.cliente cli WHERE LOWER(cli.nombre) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Credito> findByClienteNombreContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT c FROM Credito c JOIN c.cliente cli WHERE LOWER(cli.nombre) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Credito> findByClienteNombreContainingIgnoreCase(String search);


    Optional<Credito> findById(Integer id);


    Optional<Credito> findByClienteId( Integer clienteId);


    List<Credito> findByClienteCedula(String identificacion);
}
