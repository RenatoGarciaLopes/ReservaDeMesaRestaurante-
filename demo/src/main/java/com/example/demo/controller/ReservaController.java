package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.service.ReservaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ApiResponse<List<ListarReservaDto>>> listarReservas() {
        List<ListarReservaDto> reservaDtos = reservaService.listarReserva();
        return ResponseEntity.ok(new ApiResponse<>(reservaDtos));
    }

    @Operation(summary = "Listar Reserva por Cliente", description = "Listar as reservas por clientes")
    @GetMapping("cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<ListarReservaDto>>> listarReservaPorCliente(
            @PathVariable Long clienteId) {
        List<ListarReservaDto> reservaDtos = reservaService.listarReservaPorCliente(clienteId);
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
}
