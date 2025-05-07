package com.LoanTrack.Backend.Customer_service.service;

import com.LoanTrack.Backend.Customer_service.entity.Cliente;
import com.LoanTrack.Backend.Customer_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;
    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepo.findById(id);
    }
    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepo.save(cliente);
    }
    @Override
    public Cliente updateCliente(Long id, Cliente data) {
        return clienteRepo.findById(id).map(c -> {
            c.setNombre(data.getNombre());
            c.setCedula(data.getCedula());
            c.setDireccion(data.getDireccion());
            c.setTelefono(data.getTelefono());
            c.setEsNuevo(data.getEsNuevo());
            c.setTopeCredito(data.getTopeCredito());
            c.setEstado(data.getEstado());
            return clienteRepo.save(c);
        }).orElseThrow();
    }

    public void deleteCliente(Long id) {
        clienteRepo.deleteById(id);
    }
}