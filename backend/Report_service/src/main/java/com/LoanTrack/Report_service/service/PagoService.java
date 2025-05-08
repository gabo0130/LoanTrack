package com.LoanTrack.Report_service.service;


import com.LoanTrack.Report_service.entity.Pago;
import com.LoanTrack.Report_service.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface PagoService {

    public List<Pago> obtenerPagosPorPeriodo(LocalDate desde, LocalDate hasta);

}
