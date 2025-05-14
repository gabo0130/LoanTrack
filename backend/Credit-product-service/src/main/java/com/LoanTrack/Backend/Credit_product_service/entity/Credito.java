package com.LoanTrack.Backend.Credit_product_service.entity;

import com.LoanTrack.Backend.Credit_product_service.enumerations.EstadoCredito;
import com.LoanTrack.Backend.Credit_product_service.enumerations.TipoCredito;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "credito")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_credito", nullable = false)
    private TipoCredito tipoCredito;

    @Column(name = "monto_total", precision = 12, scale = 2, nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "tasa_interes", precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(name = "plazo_cuotas", nullable = false)
    private Integer plazoCuotas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCredito estado = EstadoCredito.abierto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

}
