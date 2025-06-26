package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MesaDto.AtualizarMesaDto;
import com.example.demo.dto.MesaDto.AtualizarStatusMesaDto;
import com.example.demo.dto.MesaDto.CadastrarMesaDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.enums.StatusMesa;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedMesa));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Listar Mesas", description = "Lista todas as mesas")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ListarMesaDto>>> listarMesas(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) StatusMesa status,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        Page<ListarMesaDto> mesas = mesaService.listarMesas(pagina, tamanho, status, capacidade, ativo);
        return ResponseEntity.ok(new ApiResponse<>(mesas));
    }

    @Operation(summary = "Listar Mesas Disponíveis", description = "Lista mesas com status 'Livre'")
    @GetMapping("/disponiveis")
    public ResponseEntity<ApiResponse<Page<ListarMesaDto>>> listarMesasDisponiveis(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
        Page<ListarMesaDto> mesas = mesaService.listarMesasDisponiveis(pagina, tamanho);
        return ResponseEntity.ok(new ApiResponse<>(mesas));
    }

    @Operation(summary = "Obter Mesa por ID", description = "Obtém informações de uma mesa específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarMesaDto>> obterMesaPeloId(@PathVariable Long id) {
        ListarMesaDto mesa = mesaService.obterMesaPeloId(id);
        return ResponseEntity.ok(new ApiResponse<>(mesa));
    }

    @Operation(summary = "Atualizar Mesa", description = "Atualiza os dados de uma mesa")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarMesaDto>> atualizarMesa(@PathVariable Long id,
            @RequestBody @Valid AtualizarMesaDto mesaDto) {
        ListarMesaDto mesa = mesaService.atualizarMesa(id, mesaDto);
        return ResponseEntity.ok(new ApiResponse<>(mesa));
    }

    @Operation(summary = "Alterar Status da Mesa", description = "Modifica o status de uma mesa para Livre, Ocupada ou Reservada")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ListarMesaDto>> atualizarStatusMesa(@PathVariable Long id,
            @RequestBody @Valid AtualizarStatusMesaDto mesaDto) {
        ListarMesaDto mesa = mesaService.atualizarStatusMesa(id, mesaDto);
        return ResponseEntity.ok(new ApiResponse<>(mesa));
    }

    @Operation(summary = "Inativar Mesa", description = "Inativa uma mesa do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> inativarMesa(@PathVariable Long id) {
        mesaService.inativarMesa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>("Mesa inativada com sucesso."));
    }

    @Operation(summary = "Reativar Mesa", description = "Reativa uma mesa do sistema pelo ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> reativarMesa(@PathVariable Long id) {
        mesaService.reativarMesa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>("Mesa inativada com sucesso."));
    }
}
