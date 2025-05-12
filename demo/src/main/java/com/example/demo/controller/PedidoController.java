package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.service.PedidoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Criar Pedido", description = "Registra um novo pedido")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarPedidoDto>> criarPedido(@RequestBody @Valid CadastrarPedidoDto pedidoDto) {
        try {
            ListarPedidoDto savedPedido = pedidoService.salvar(pedidoDto);
            ApiResponse<ListarPedidoDto> response = new ApiResponse<>(savedPedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarPedidoDto> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar Pedidos", description = "Lista todos os pedidos")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarPedidoDto>>> listarPedidos() {
        List<ListarPedidoDto> pedidosDto = pedidoService.listarPedidos();
        ApiResponse<List<ListarPedidoDto>> response = new ApiResponse<>(pedidosDto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Pedidos por Reserva", description = "Lista os pedidos associados a uma reserva específica")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ApiResponse<List<ListarPedidoDto>>> listarPedidoPorReserva(
            @PathVariable Long reservaId) {
        List<ListarPedidoDto> pedidosDto = pedidoService.listarPedidoPorReserva(reservaId);
        ApiResponse<List<ListarPedidoDto>> response = new ApiResponse<>(pedidosDto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter Pedido por ID", description = "Obtém os detalhes de um pedido específico por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarPedidoDto>> obterPedidoPorId(@PathVariable Long id) {
        ListarPedidoDto dto = pedidoService.obterPedidoPorId(id);
        ApiResponse<ListarPedidoDto> response = new ApiResponse<>(dto);

        return ResponseEntity.ok(response);
    }
}
