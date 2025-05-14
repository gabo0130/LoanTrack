package com.LoanTrack.Backend.Credit_product_service.repository;
import com.LoanTrack.Backend.Credit_product_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCedula(String identificacion);
    boolean existsByCedula(String identificacion);

}
