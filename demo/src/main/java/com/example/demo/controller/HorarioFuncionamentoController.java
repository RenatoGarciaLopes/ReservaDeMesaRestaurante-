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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HorarioFuncionamentoDto.AtualizarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.CadastrarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.ListarHorarioFuncionamento;
import com.example.demo.service.HorarioFuncionamentoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Horários de Funcionamento", description = "Endpoints para gerenciamento de horários de funcionamento")
@RestController
@RequestMapping("api/horarios-funcionamento")
public class HorarioFuncionamentoController {

    @Autowired
    private HorarioFuncionamentoService horarioFuncionamentoService;

    @Operation(summary = "Registrar Horário de Funcionamento", description = "Cadastrar um novo horário de funcionamento")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarHorarioFuncionamento>> criarHorarioFuncionamento(
            @RequestBody @Valid CadastrarHorarioFuncionamento dto) {
        try {
            ListarHorarioFuncionamento savedHorarioFuncionamento = horarioFuncionamentoService.cadastrar(dto);
            ApiResponse<ListarHorarioFuncionamento> response = new ApiResponse<>(savedHorarioFuncionamento);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarHorarioFuncionamento> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar Horários", description = "Lista todos os horários")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarHorarioFuncionamento>>> listarMesas() {
        List<ListarHorarioFuncionamento> horarios = horarioFuncionamentoService.listarHorarios();
        ApiResponse<List<ListarHorarioFuncionamento>> response = new ApiResponse<>(horarios);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter Horário por ID", description = "Obtém informações dos horários de um dia específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarHorarioFuncionamento>> obterMesaPeloId(@PathVariable Long id) {
        ListarHorarioFuncionamento horario = horarioFuncionamentoService.obterHorarioPorId(id);
        ApiResponse<ListarHorarioFuncionamento> response = new ApiResponse<>(horario);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar Horário de Funcionamento", description = "Atualiza os dados de um dia de funcionamento")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarHorarioFuncionamento>> atualizarHorario(@PathVariable Long id,
            @RequestBody AtualizarHorarioFuncionamento dto) {
        ListarHorarioFuncionamento mesa = horarioFuncionamentoService.atualizarHorario(id, dto);
        ApiResponse<ListarHorarioFuncionamento> response = new ApiResponse<>(mesa);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover Horário de Funcionamento", description = "Remove um dia de funcionamento do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerMesa(@PathVariable Long id) {
        horarioFuncionamentoService.removerHorario(id);
        return ResponseEntity.noContent().build();
    }
}
