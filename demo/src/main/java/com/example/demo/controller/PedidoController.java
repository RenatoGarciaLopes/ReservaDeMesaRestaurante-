package com.example.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.dto.PedidoDto.PedidoDetalhadoDto;
import com.example.demo.dto.PedidoDto.PedidoResumoDto;
import com.example.demo.dto.PedidoDto.EstatisticasReservaDto;
import com.example.demo.dto.PedidoDto.PedidoExportacaoCsvDto;
import com.example.demo.enums.StatusPedido;
import com.example.demo.service.CsvService;
import com.example.demo.service.PedidoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CsvService csvService;

    @Operation(summary = "Criar Pedido", description = "Registra um novo pedido")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarPedidoDto>> criarPedido(@RequestBody @Valid CadastrarPedidoDto pedidoDto) {
        try {
            ListarPedidoDto savedPedido = pedidoService.salvar(pedidoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedPedido));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Listar Pedidos", description = "Lista todos os pedidos")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ListarPedidoDto>>> listarPedidos(
            @RequestParam(required = false) StatusPedido status,
            @RequestParam(required = false) String nomeItem,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
        Page<ListarPedidoDto> pedidosDto = pedidoService.listarPedidos(pagina, tamanho, status, nomeItem);
        return ResponseEntity.ok(new ApiResponse<>(pedidosDto));
    }

    @Operation(summary = "Listar Pedidos por Reserva", description = "Lista os pedidos associados a uma reserva específica")
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ApiResponse<Page<ListarPedidoDto>>> listarPedidoPorReserva(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @PathVariable Long reservaId) {
        Page<ListarPedidoDto> pedidosDto = pedidoService.listarPedidoPorReserva(pagina, tamanho, reservaId);
        return ResponseEntity.ok(new ApiResponse<>(pedidosDto));
    }

    @Operation(summary = "Listar Resumo de Pedidos por Reserva", description = "Lista um resumo limpo dos pedidos associados a uma reserva específica")
    @GetMapping("/reserva/{reservaId}/resumo")
    public ResponseEntity<ApiResponse<Page<PedidoResumoDto>>> listarPedidoResumoPorReserva(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @PathVariable Long reservaId) {
        Page<PedidoResumoDto> pedidosDto = pedidoService.listarPedidoResumoPorReserva(pagina, tamanho, reservaId);
        return ResponseEntity.ok(new ApiResponse<>(pedidosDto));
    }

    @Operation(summary = "Listar Detalhes de Pedidos por Reserva", description = "Lista os detalhes completos dos pedidos associados a uma reserva específica")
    @GetMapping("/reserva/{reservaId}/detalhes")
    public ResponseEntity<ApiResponse<Page<PedidoDetalhadoDto>>> listarPedidoDetalhadoPorReserva(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @PathVariable Long reservaId) {
        Page<PedidoDetalhadoDto> pedidosDto = pedidoService.listarPedidoDetalhadoPorReserva(pagina, tamanho, reservaId);
        return ResponseEntity.ok(new ApiResponse<>(pedidosDto));
    }

    @Operation(summary = "Obter Estatísticas da Reserva", description = "Retorna estatísticas resumidas dos pedidos de uma reserva")
    @GetMapping("/reserva/{reservaId}/estatisticas")
    public ResponseEntity<ApiResponse<EstatisticasReservaDto>> obterEstatisticasReserva(@PathVariable Long reservaId) {
        EstatisticasReservaDto estatisticas = pedidoService.obterEstatisticasReserva(reservaId);
        return ResponseEntity.ok(new ApiResponse<>(estatisticas));
    }

    @Operation(summary = "Obter Pedido por ID", description = "Obtém os detalhes de um pedido específico por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarPedidoDto>> obterPedidoPorId(@PathVariable Long id) {
        ListarPedidoDto dto = pedidoService.obterPedidoPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(dto));
    }

    @Operation(summary = "Cancelar Pedido", description = "Cancela um pedido existente")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> removerPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>("Pedido cancelado com sucesso."));
    }

    @Operation(summary = "Exportar pedidos em csv", description = "Exporta pedidos por mesa para arquivo csv")
    @GetMapping("/exportar")
    public ResponseEntity<Void> exportarArquivoCSV(HttpServletResponse response)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, URISyntaxException {
        String fileName = "pedidos-por-mesa.csv";
        List<PedidoExportacaoCsvDto> requestList = pedidoService.convertePedidosCsv();

        csvService.exportCsv(fileName, response, requestList, PedidoExportacaoCsvDto.class);

        return ResponseEntity.ok().build();
    }
}
