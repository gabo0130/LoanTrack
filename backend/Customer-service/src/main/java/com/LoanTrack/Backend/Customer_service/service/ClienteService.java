package com.LoanTrack.Backend.Customer_service.service;


import com.LoanTrack.Backend.Customer_service.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> getAllClientes();

    Optional<Cliente> getClienteById(Long id);

    Cliente createCliente(Cliente cliente);

    Cliente updateCliente(Long id, Cliente data);
}
