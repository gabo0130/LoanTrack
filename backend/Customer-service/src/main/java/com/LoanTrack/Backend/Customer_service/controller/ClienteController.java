
package com.LoanTrack.Backend.Customer_service.controller;

import com.LoanTrack.Backend.Customer_service.entity.*;
import com.LoanTrack.Backend.Customer_service.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> getAll() {
        return service.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return service.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente c) {
        return service.createCliente(c);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody Cliente c) {
        return service.updateCliente(id, c);
    }
//
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
