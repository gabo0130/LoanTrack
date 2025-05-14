package com.LoanTrack.Backend.Credit_product_service.entity;

import com.LoanTrack.Backend.Credit_product_service.enumerations.EstadoCredito;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuota")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credito", nullable = false)
    @JsonIgnore
    private Credito credito;

    // Relación inversa con pagos
//    @OneToMany(
//            mappedBy = "cuota",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
    @OneToMany(mappedBy = "cuota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Maneja el lado "padre" de la relación JSON
    private List<Pago> pagos = new ArrayList<>();

    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @Column(name = "monto_cuota", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoCuota;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCredito estado;

    @Column(name = "saldo_pendiente", precision = 10, scale = 2)
    private BigDecimal saldoPendiente;

    // 👇 Constructor necesario para usar agregarPago(new Pago(...))
    public void Pago(BigDecimal montoPagado) {
        this.montoCuota = montoCuota;
    }

    // Método de conveniencia para agregar pagos
    public void agregarPago(Pago pago) {
        pagos.add(pago);
        pago.setCuota(this);
        this.actualizarEstado();
    }

    public void registrarPago(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        this.saldoPendiente = this.saldoPendiente.subtract(monto);

        if (this.saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
            this.estado = EstadoCredito.cancelado;
        } else if (this.estado == EstadoCredito.moroso) {
            this.estado = EstadoCredito.parcial;
        }
    }

    // Método para actualizar estado según pagos
    private void actualizarEstado() {
        BigDecimal totalPagado = pagos.stream()
                .map(Pago::getMontoPagado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.saldoPendiente = montoCuota.subtract(totalPagado);

        if (saldoPendiente.compareTo(BigDecimal.ZERO) <= 0) {
            this.estado = EstadoCredito.cancelado;
        } else if (LocalDate.now().isAfter(fechaVencimiento)) {
            this.estado = EstadoCredito.moroso;
        }
    }

    public static LocalDate calcularFechaVencimiento(LocalDate fechaInicio, int numeroCuota) {
        return fechaInicio.plusMonths(numeroCuota);
    }
}