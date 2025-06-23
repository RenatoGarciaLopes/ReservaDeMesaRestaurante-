package com.example.demo.repository.specification;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusReserva;

public class ReservaSpecification {

    public static Specification<Reserva> temData(LocalDate dataReserva) {
        return (root, query, cb) ->
            dataReserva == null ? null : cb.equal(root.get("dataReserva"), dataReserva);
    }

    public static Specification<Reserva> temHorario(LocalTime horarioReserva) {
        return (root, query, cb) ->
            horarioReserva == null ? null : cb.equal(root.get("horaReserva"), horarioReserva);
    }

    public static Specification<Reserva> temStatus(StatusReserva status) {
        return (root, query, cb) ->
            status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Reserva> temMesa(Long mesaId) {
        return (root, query, cb) ->
            mesaId == null ? null : cb.equal(root.get("mesa").get("id"), mesaId);
    }
}
