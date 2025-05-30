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

import com.example.demo.dto.AtualizarStatusReservaDto;
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
            ApiResponse<ListarReservaDto> response = new ApiResponse<>(savedReservaDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarReservaDto> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar Reservas", description = "Lista de todas as Reservas")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarReservaDto>>> listarReservas() {
        List<ListarReservaDto> reservaDtos = reservaService.listarReserva();
        ApiResponse<List<ListarReservaDto>> response = new ApiResponse<>(reservaDtos);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Reserva por Cliente", description = "Listar as reservas por clientes")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<ListarReservaDto>>> listarReservaPorCliente(
            @PathVariable Long clienteId) {
        List<ListarReservaDto> reservaDtos = reservaService.listarReservaPorCliente(clienteId);
        ApiResponse<List<ListarReservaDto>> response = new ApiResponse<>(reservaDtos);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter reserva por ID", description = "Obter a reserva por ID especifico")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarReservaDto>> obterReservaPorId(@PathVariable Long id){
        ListarReservaDto dto = reservaService.obterReservaPorId(id);
        ApiResponse<ListarReservaDto> response = new ApiResponse<>(dto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar Reserva", description = "Atualiza status da mesa")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarReservaDto>> atualizarReserva(@PathVariable Long id,
                @RequestBody @Valid AtualizarStatusReservaDto reservaDto){
            ListarReservaDto reserva = reservaService.atualizarStatusReserva(id, reservaDto);
            ApiResponse<ListarReservaDto> response = new ApiResponse<>(reserva);

            return ResponseEntity.ok(response);
    }


    @Operation(summary = "Remover Reserva", description = "Remove uma reserva")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerReserva(@PathVariable Long id){
        reservaService.removerReserva(id);

        return ResponseEntity.noContent().build();
    }


}
