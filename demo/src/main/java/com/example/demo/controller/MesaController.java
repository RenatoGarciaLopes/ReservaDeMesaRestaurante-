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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MesaDto.AtualizarMesaDto;
import com.example.demo.dto.MesaDto.AtualizarStatusMesaDto;
import com.example.demo.dto.MesaDto.CadastrarMesaDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.service.MesaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Mesas", description = "Endpoints para gerenciamento de mesas")
@RestController
@RequestMapping("api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Operation(summary = "Registrar Mesa", description = "Cadastra uma nova mesa")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarMesaDto>> criarMesa(@RequestBody @Valid CadastrarMesaDto mesaDto) {
        try {
            ListarMesaDto savedMesa = mesaService.salvar(mesaDto);
            ApiResponse<ListarMesaDto> response = new ApiResponse<>(savedMesa);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarMesaDto> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar Mesas", description = "Lista todas as mesas")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarMesaDto>>> listarMesas() {
        List<ListarMesaDto> mesas = mesaService.listarMesas();
        ApiResponse<List<ListarMesaDto>> response = new ApiResponse<>(mesas);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Mesas Disponíveis", description = "Lista mesas com status 'Livre'")
    @GetMapping("/disponiveis")
    public ResponseEntity<ApiResponse<List<ListarMesaDto>>> listarMesasDisponiveis() {
        List<ListarMesaDto> mesas = mesaService.listarMesasDisponiveis();
        ApiResponse<List<ListarMesaDto>> response = new ApiResponse<>(mesas);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter Mesa por ID", description = "Obtém informações de uma mesa específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarMesaDto>> obterMesaPeloId(@PathVariable Long id) {
        ListarMesaDto mesa = mesaService.obterMesaPeloId(id);
        ApiResponse<ListarMesaDto> response = new ApiResponse<>(mesa);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar Mesa", description = "Atualiza os dados de uma mesa")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarMesaDto>> atualizarMesa(@PathVariable Long id,
            @RequestBody @Valid AtualizarMesaDto mesaDto) {
        ListarMesaDto mesa = mesaService.atualizarMesa(id, mesaDto);
        ApiResponse<ListarMesaDto> response = new ApiResponse<>(mesa);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Alterar Status da Mesa", description = "Modifica o status de uma mesa para Livre, Ocupada ou Reservada")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ListarMesaDto>> atualizarStatusMesa(@PathVariable Long id,
            @RequestBody @Valid AtualizarStatusMesaDto mesaDto) {
        ListarMesaDto mesa = mesaService.atualizarStatusMesa(id, mesaDto);
        ApiResponse<ListarMesaDto> response = new ApiResponse<>(mesa);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover Mesa", description = "Remove uma mesa do sistema pelo ID, desde que não tenha reservas associadas")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerMesa(@PathVariable Long id) {
        mesaService.removerMesa(id);
        return ResponseEntity.noContent().build();
    }
}
