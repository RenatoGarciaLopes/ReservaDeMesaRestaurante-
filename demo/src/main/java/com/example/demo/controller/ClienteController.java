package com.example.demo.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ClienteDto.AtualizarClienteDto;
import com.example.demo.dto.ClienteDto.CadastroClienteDto;
import com.example.demo.dto.ClienteDto.ListarClienteDto;
import com.example.demo.service.ClienteService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
            ApiResponse<ListarClienteDto> response = new ApiResponse<>(savedCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarClienteDto> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @Operation(summary = "Listar cliente", description = "Listar todos os clientes")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarClienteDto>>> listarClientes() {
        List<ListarClienteDto> cliente = clienteService.listarClientesDisponiveis();
        ApiResponse<List<ListarClienteDto>> response = new ApiResponse<>(cliente);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter Cliente por ID", description = "Obtém infotmações de um cliente específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarClienteDto>> obterClientePeloId(@PathVariable long id) {
        ListarClienteDto cliente = clienteService.obterClientePeloId(id);
        ApiResponse<ListarClienteDto> response = new ApiResponse<>(cliente);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar Cliente", description = "Atualizar dados do cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarClienteDto>> atualizarCliente(@PathVariable long id, @RequestBody @Valid AtualizarClienteDto clienteDto){
        ListarClienteDto cliente = clienteService..atualizarCliente(id, clienteDto);
        ApiResponse<ListarClienteDto> response = new ApiResponse<>(cliente);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover Cliente", description = "Remove um cliemte do sistema pelo ID, desde que não tenha reservas associadas")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}
