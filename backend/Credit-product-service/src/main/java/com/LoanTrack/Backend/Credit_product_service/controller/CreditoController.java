package com.LoanTrack.Backend.Credit_product_service.controller;

import com.LoanTrack.Backend.Credit_product_service.entity.Credito;
import com.LoanTrack.Backend.Credit_product_service.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping
    public Page<Credito> getCreditos(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return creditoService.getCreditosWithPagination(search, page, size);
    }

    // Buscar créditos por nombre del cliente (sin paginación)
    @GetMapping("/buscar/{nombre}")
    public List<Credito> buscarPorNombreCliente( @PathVariable String nombre) {
        return creditoService.findByNombre(nombre);
    }

    // GET /api/creditos/{id}
    @GetMapping("/{id}")
    public Credito getCreditoById(@PathVariable Integer id) {
        return creditoService.getCreditoById(id);
    }

    // POST /api/creditos
    @PostMapping
    public Credito crearCredito(@RequestBody Credito credito) {
        return creditoService.createCredito(credito);
    }

    // PUT /api/creditos/{id}
    @PutMapping("/{id}")
    public Credito actualizarCredito( @RequestBody Credito credito, @PathVariable Integer id) {
        return creditoService.updateCredito(id, credito);
    }

}
