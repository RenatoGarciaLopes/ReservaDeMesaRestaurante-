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

import com.example.demo.dto.FuncionarioDto.AlterarSenhaDto;
import com.example.demo.dto.FuncionarioDto.AtualizarFuncionarioDto;
import com.example.demo.dto.FuncionarioDto.CadastrarFuncionarioDto;
import com.example.demo.dto.FuncionarioDto.ListarFuncionarioDto;
import com.example.demo.enums.Cargo;
import com.example.demo.service.FuncionarioService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Funcionários", description = "Endpoints para gerenciamento de Funcionário")
@RestController
@RequestMapping("api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Operation(summary = "Registrar funcionário", description = "Cadastrar um novo funcionário")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarFuncionarioDto>> criarFuncionario(
            @RequestBody @Valid CadastrarFuncionarioDto dto) {
        try {
            ListarFuncionarioDto savedFuncionario = funcionarioService.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedFuncionario));
        } catch (Exception e) {
            ApiResponse<ListarFuncionarioDto> response = new ApiResponse<>(
                    new ErrorResponse("Erro interno", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar funcionários", description = "Listar todos os funcionários")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ListarFuncionarioDto>>> listarFuncionarios(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Cargo cargo,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
        Page<ListarFuncionarioDto> pageDtos = funcionarioService.listarFuncionarios(pagina, tamanho,
                nome, cargo, status);
        return ResponseEntity.ok(new ApiResponse<>(pageDtos));
    }

    @Operation(summary = "Obter funcionários por ID", description = "Obtém informações de um cliente específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarFuncionarioDto>> obterFuncionarioPeloId(@PathVariable long id) {
        ListarFuncionarioDto func = funcionarioService.obterFuncionarioPeloId(id);
        return ResponseEntity.ok(new ApiResponse<>(func));
    }

    @Operation(summary = "Buscar funcionário por CPF", description = "Busca um funcionário por CPF")
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<ListarFuncionarioDto>> buscarPorCpf(@RequestParam String cpf) {
        ListarFuncionarioDto func = funcionarioService.obterFuncionarioPeloCpf(cpf);
        return ResponseEntity.ok(new ApiResponse<>(func));
    }

    @Operation(summary = "Atualizar funcionário", description = "Atualizar dados de um funcionário")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarFuncionarioDto>> atualizarFuncionario(@PathVariable long id,
            @RequestBody @Valid AtualizarFuncionarioDto dto) {
        ListarFuncionarioDto func = funcionarioService.atualizarFuncionario(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(func));
    }

    @Operation(summary = "Inativar funcionário", description = "Inativa um funcionário do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> inativarFuncionario(@PathVariable long id) {
        funcionarioService.inativarFuncionario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>("Funcionário inativado com sucesso."));
    }

    @Operation(summary = "Reativar funcionários", description = "Reativa um funcionário do sistema pelo ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> reativarFuncionario(@PathVariable long id) {
        funcionarioService.reativarFuncionario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>("Funcionário reativado com sucesso."));
    }

    @Operation(summary = "Alterar minha senha", description = "Altera a senha do funcionário logado")
    @PatchMapping("/senha")
    public ResponseEntity<ApiResponse<String>> alterarMinhaSenha(
            @RequestBody @Valid AlterarSenhaDto dto,
            @RequestParam String email) {
        try {
            funcionarioService.alterarMinhaSenha(email, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmarNovaSenha());
            return ResponseEntity.ok(new ApiResponse<>("Senha alterada com sucesso."));
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(new ErrorResponse("Erro de validação", e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>(new ErrorResponse("Funcionário não encontrado", e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(new ErrorResponse("Erro interno", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Alterar senha do funcionário (ADMIN)", description = "Altera a senha de um funcionário específico (apenas para administradores)")
    @PatchMapping("/{id}/senha")
    public ResponseEntity<ApiResponse<String>> alterarSenha(@PathVariable long id,
            @RequestBody @Valid AlterarSenhaDto dto) {
        try {
            funcionarioService.alterarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmarNovaSenha());
            return ResponseEntity.ok(new ApiResponse<>("Senha alterada com sucesso."));
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(new ErrorResponse("Erro de validação", e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>(new ErrorResponse("Funcionário não encontrado", e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(new ErrorResponse("Erro interno", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
