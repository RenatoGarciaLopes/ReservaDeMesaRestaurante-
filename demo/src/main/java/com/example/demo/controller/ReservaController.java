package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.enums.StatusReserva;
import com.example.demo.service.ReservaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Tag(name = "Reservas", description = "Endpoints para gerenciamento de reservas")
@RestController
@RequestMapping("api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Registrar Reserva", description = "Registra uma nova reserva")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarReservaDto>> criarReserva(
            @RequestBody @Valid CadastrarReservaDTO reservaDTO) {
        try {
            ListarReservaDto savedReservaDto = reservaService.salvar(reservaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedReservaDto));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Listar Reservas", description = "Lista de todas as Reservas")
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listarReservas(
            @RequestParam(required = false) LocalDate dataReserva,
            @Parameter(description = "Horário no formato HH:mm") @RequestParam(required = false) String horarioReserva,
            @RequestParam(required = false) StatusReserva status,
            @RequestParam(required = false) Long mesaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanho) {

        LocalTime horarioConvertido = null;
        if (horarioReserva != null && !horarioReserva.isBlank()) {
            try {
                horarioConvertido = LocalTime.parse(horarioReserva);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Formato inválido para horário. Use HH:mm"));
            }
        }

        Page<ListarReservaDto> reservaDtos = reservaService.listarReserva(pagina, tamanho,
                dataReserva, horarioConvertido, status, mesaId);
        return ResponseEntity.ok(new ApiResponse<>(reservaDtos));
    }

    @Operation(summary = "Listar Reserva por Cliente", description = "Listar as reservas por clientes")
    @GetMapping("cliente/{clienteId}")
    public ResponseEntity<ApiResponse<Page<ListarReservaDto>>> listarReservaPorCliente(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @PathVariable Long clienteId) {
        Page<ListarReservaDto> reservaDtos = reservaService.listarReservaPorCliente(pagina, tamanho, clienteId);
        return ResponseEntity.ok(new ApiResponse<>(reservaDtos));
    }

    @Operation(summary = "Obter reserva por ID", description = "Obter a reserva por ID especifico")
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ListarReservaDto>> obterReservaPorId(@PathVariable Long id) {
        ListarReservaDto dto = reservaService.obterReservaPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(dto));
    }

    @Operation(summary = "Confirmar chegada do cliente", description = "Atualiza o status da mesa para 'OCUPADA'")
    @PatchMapping("{id}/confirmar")
    public ResponseEntity<ApiResponse<ListarReservaDto>> confirmarChegada(@PathVariable Long id) {
        ListarReservaDto reserva = reservaService.confirmarChegadaReserva(id);
        return ResponseEntity.ok(new ApiResponse<>(reserva));
    }

    @Operation(summary = "Concluir reserva", description = "Atualiza o status da reserva para 'CONCLUÍDA' e libera a mesa (status 'LIVRE')")
    @PatchMapping("{id}/concluir")
    public ResponseEntity<ApiResponse<ListarReservaDto>> concluirReserva(@PathVariable Long id) {
        ListarReservaDto reserva = reservaService.concluirReserva(id);
        return ResponseEntity.ok(new ApiResponse<>(reserva));
    }

    @Operation(summary = "Cancelar Reserva", description = "Atualiza o status da reserva para 'CANCELADA' e libera a mesa (status 'LIVRE')")
    @DeleteMapping("{id}/cancelar")
    public ResponseEntity<ApiResponse<ListarReservaDto>> cancelarReserva(@PathVariable Long id) {
        ListarReservaDto reserva = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(new ApiResponse<>(reserva));
    }

    @Operation(summary = "Listar horários disponíveis para uma mesa", description = "Retorna uma lista de horários disponíveis para uma mesa específica em uma dada data.")
    @GetMapping("/disponibilidade")
    public ResponseEntity<ApiResponse<List<LocalTime>>> listarHorariosDisponiveis(
            @RequestParam @Parameter(description = "ID da mesa") Long mesaId,
            @RequestParam @Parameter(description = "Data no formato YYYY-MM-DD") LocalDate dataConsulta) {
        try {
            List<LocalTime> horarios = reservaService.listarHorariosDisponiveisParaMesa(mesaId, dataConsulta);
            return ResponseEntity.ok(new ApiResponse<>(horarios));
        } catch (EntityNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro de Entidade", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(errorResponse));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }
}
