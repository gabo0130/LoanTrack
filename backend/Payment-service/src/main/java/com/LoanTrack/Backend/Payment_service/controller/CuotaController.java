
package com.LoanTrack.Backend.Payment_service.controller;

import com.LoanTrack.Backend.Payment_service.entity.*;
import com.LoanTrack.Backend.Payment_service.service.CuotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @GetMapping
    public List<Cuota> getAllCuotas() {
        return cuotaService.getAllCuotas();
    }
    @GetMapping("/{id}")
    public Optional<Cuota> getCuotaById(@PathVariable Long id) {
        return cuotaService.getCuotaById(id);
    }


    @GetMapping("creditos/{creditoId}")
    public List<Cuota> getCuotasByCreditoId(@PathVariable Long creditoId) {
        return cuotaService.getAllCuotasByCreditoId(creditoId);
    }

    @PostMapping
    public Cuota createCuota(@RequestBody Cuota cuota) {
        return cuotaService.createCuota(cuota);
    }

    @PutMapping("/{id}")
    public Cuota updateCuota(@PathVariable Long id, @RequestBody Cuota cuota) {
        return cuotaService.updateCuota(id, cuota);
    }

    @DeleteMapping("/{id}")
    public void deleteCuota(@PathVariable Long id) {
        cuotaService.deleteCuota(id);
    }


}
