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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoriaDto.CadastrarCategoriaDto;
import com.example.demo.dto.CategoriaDto.ListarCategoriaDto;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias dos itens de cardápio")
@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Registrar Categoria", description = "Cadastra uma nova categoria")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarCategoriaDto>> cadastrarCategoria(
            @RequestBody @Valid CadastrarCategoriaDto dto) {
        try {
            ListarCategoriaDto savedCategoria = categoriaService.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedCategoria));
        } catch (Exception e) {
            ApiResponse<ListarCategoriaDto> response = new ApiResponse<>(new ErrorResponse("Erro interno", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar Categorias")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarCategoriaDto>>> listarCategorias(
            @RequestParam(required = false) Boolean ativo) {

        List<ListarCategoriaDto> categorias;

        if (ativo == null) {
            categorias = categoriaService.listarTodasCategorias();
        } else {
            categorias = categoriaService.listarCategoriasPorStatus(ativo);
        }

        return ResponseEntity.ok(new ApiResponse<>(categorias));
    }

    @Operation(summary = "Obter Categoria por ID", description = "Obtém uma categoria específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarCategoriaDto>> obterMesaPeloId(@PathVariable Long id) {
        ListarCategoriaDto categoria = categoriaService.obterCategoriaPeloId(id);
        return ResponseEntity.ok(new ApiResponse<>(categoria));
    }

    @Operation(summary = "Atualizar Categoria", description = "Atualiza os dados de uma categoria")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarCategoriaDto>> atualizarCategoria(@PathVariable Long id,
            @RequestBody @Valid CadastrarCategoriaDto dto) {
        ListarCategoriaDto categoria = categoriaService.atualizarCategoria(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(categoria));
    }

    @Operation(summary = "Inativa Categoria", description = "Inativa uma categoria")
    @DeleteMapping("{id}/inativar")
    public ResponseEntity<ApiResponse<String>> inativarCategoria(@PathVariable Long id) {
        categoriaService.inativarCategoria(id);
        return ResponseEntity.ok(new ApiResponse<>("Categoria inativada com sucesso"));
    }

    @Operation(summary = "Ativar Categoria", description = "Reativa uma categoria inativa")
    @PatchMapping("{id}/ativar")
    public ResponseEntity<ApiResponse<String>> ativarCategoria(@PathVariable Long id) {
        categoriaService.reativarCategoria(id);
        return ResponseEntity.ok(new ApiResponse<>("Categoria ativada com sucesso"));
    }
}
