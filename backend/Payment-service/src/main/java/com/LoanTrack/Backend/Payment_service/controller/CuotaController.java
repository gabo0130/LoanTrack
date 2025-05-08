
package com.LoanTrack.Backend.Payment_service.controller;

import com.LoanTrack.Backend.Payment_service.entity.*;
import com.LoanTrack.Backend.Payment_service.service.CuotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    // Obtener todas las cuotas
    @GetMapping
    public List<Cuota> getAllCuotas() {
        return cuotaService.getAllCuotas();
    }

    // Obtener cuota por ID
    @GetMapping("/{id}")
    public Optional<Cuota> getCuotaById(@PathVariable Long id) {
        return cuotaService.getCuotaById(id);
    }

    // Crear nueva cuota
    @PostMapping
    public Cuota createCuota(@RequestBody Cuota cuota) {
        return cuotaService.createCuota(cuota);
    }

    // Actualizar cuota
    @PutMapping("/{id}")
    public Cuota updateCuota(@PathVariable Long id, @RequestBody Cuota cuota) {
        return cuotaService.updateCuota(id, cuota);
    }

    // Eliminar cuota
    @DeleteMapping("/{id}")
    public void deleteCuota(@PathVariable Long id) {
        cuotaService.deleteCuota(id);
    }
}//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        service.deleteCliente(id);
//    }
//
//    @GetMapping("/{id}/references")
//    public List<ClienteReferencia> getReferencias(@PathVariable Long id) {
//        return service.getReferencias(id);
//    }
//
//    @PostMapping("/{id}/references")
//    public ClienteReferencia addReferencia(@PathVariable Long id, @RequestBody Long referenciaId) {
//        return service.addReferencia(id, referenciaId);
//    }
}
