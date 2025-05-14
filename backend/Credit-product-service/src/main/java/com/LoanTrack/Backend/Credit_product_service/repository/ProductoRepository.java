package com.LoanTrack.Backend.Credit_product_service.repository;

import com.LoanTrack.Backend.Credit_product_service.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
}
