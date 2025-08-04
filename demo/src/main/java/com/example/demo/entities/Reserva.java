package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.demo.enums.StatusReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Getter
@Setter
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    @JsonBackReference
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = true)
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDate dataReserva;

    @Column(nullable = false)
    private LocalTime horaReserva;

    @Column(nullable = false)
    private Integer quantidadePessoas;

    @Column(nullable = false)
    private StatusReserva status = StatusReserva.ATIVA;

    @OneToMany(mappedBy = "reserva")
    @JsonManagedReference
    private List<Pedido> pedidos;
}
