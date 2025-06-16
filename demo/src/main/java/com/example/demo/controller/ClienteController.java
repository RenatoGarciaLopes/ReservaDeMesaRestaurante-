package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ClienteDto.AtualizarClienteDto;
import com.example.demo.dto.ClienteDto.CadastroClienteDto;
import com.example.demo.dto.ClienteDto.ListarClienteDto;
import com.example.demo.service.ClienteService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Clientes", description = "Endpoints para gerenciamento de Cliente")
@RestController
@RequestMapping("api/clientes")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Registrar Cliente", description = "Cadastrar novo cliente")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarClienteDto>> criarCliente(
            @RequestBody @Valid CadastroClienteDto clienteDto) {
        try {
            ListarClienteDto savedCliente = clienteService.salvar(clienteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedCliente));
        } catch (Exception e) {
            ApiResponse<ListarClienteDto> response = new ApiResponse<>(
                    new ErrorResponse("Erro interno", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar cliente", description = "Listar todos os clientes")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarClienteDto>>> listarClientes() {
        List<ListarClienteDto> cliente = clienteService.listarCliente();
        return ResponseEntity.ok(new ApiResponse<>(cliente));
    }

    @Operation(summary = "Obter Cliente por ID", description = "Obtém infotmações de um cliente específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarClienteDto>> obterClientePeloId(@PathVariable long id) {
        ListarClienteDto cliente = clienteService.obterClientePeloId(id);
        return ResponseEntity.ok(new ApiResponse<>(cliente));
    }

    @Operation(summary = "Buscar Cliente por CPF", description = "Busca um cliente por CPF")
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<ListarClienteDto>> buscarPorCpf(@RequestParam String cpf) {
        ListarClienteDto cliente = clienteService.obterClientePeloCpf(cpf);
        return ResponseEntity.ok(new ApiResponse<>(cliente));
    }

    @Operation(summary = "Atualizar Cliente", description = "Atualizar dados do cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarClienteDto>> atualizarCliente(@PathVariable long id,
            @RequestBody @Valid AtualizarClienteDto clienteDto) {
        ListarClienteDto cliente = clienteService.atualizarCliente(id, clienteDto);
        return ResponseEntity.ok(new ApiResponse<>(cliente));
    }

    @Operation(summary = "Remover Cliente", description = "Remove um cliente do sistema pelo ID, desde que não tenha reservas associadas")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removerCliente(@PathVariable long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>("Cliente removido com sucesso."));
    }
}