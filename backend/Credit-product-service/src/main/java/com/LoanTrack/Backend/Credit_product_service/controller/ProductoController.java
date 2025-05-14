package com.LoanTrack.Backend.Credit_product_service.controller;

import com.LoanTrack.Backend.Credit_product_service.dto.ProductoDto;
import com.LoanTrack.Backend.Credit_product_service.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody ProductoDto productoDTO) {
        ProductoDto response = ProductoDto.fromEntity(
                productoService.crearProducto(productoDTO.toEntity()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerProducto(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ProductoDto.fromEntity(productoService.obtenerProductoPorId(id)));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody ProductoDto productoDTO) {

        return ResponseEntity.ok(
                ProductoDto.fromEntity(
                        productoService.actualizarProducto(id, productoDTO.toEntity())));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
