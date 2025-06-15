package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enums.StatusReserva;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "reservas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mesa")
    private Mesa mesa;

    @Column(nullable = false)
    private LocalDate dataReserva;

    @Column(nullable = false)
    private LocalTime horaReserva;

    @Column(nullable = false)
    private Integer quantidadePessoas;

    @Column(nullable = false)
    private StatusReserva status = StatusReserva.CONFIRMADA;
}
