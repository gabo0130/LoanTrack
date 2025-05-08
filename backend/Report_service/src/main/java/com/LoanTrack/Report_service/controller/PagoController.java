
package com.LoanTrack.Report_service.controller;

import com.LoanTrack.Report_service.entity.*;
import com.LoanTrack.Report_service.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class
PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/consulta-periodo")
    public List<Pago> getPagosPorPeriodo(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return pagoService.obtenerPagosPorPeriodo(desde, hasta);
    }

}
