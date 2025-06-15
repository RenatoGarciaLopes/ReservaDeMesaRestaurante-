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
            ApiResponse<ListarCategoriaDto> response = new ApiResponse<>(savedCategoria);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarCategoriaDto> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar Categorias", description = "Lista todas as categorias")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarCategoriaDto>>> listarCategorias() {
        List<ListarCategoriaDto> categorias = categoriaService.listarCliente();
        ApiResponse<List<ListarCategoriaDto>> response = new ApiResponse<>(categorias);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter Categoria por ID", description = "Obtém uma categoria específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarCategoriaDto>> obterMesaPeloId(@PathVariable Long id) {
        ListarCategoriaDto categoria = categoriaService.obterCategoriaPeloId(id);
        ApiResponse<ListarCategoriaDto> response = new ApiResponse<>(categoria);

        return ResponseEntity.ok(response);
    }
}
