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

import com.example.demo.dto.ItensDeCardapioDto.AtualizarItemDto;
import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.service.ItensService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Itens de cardapio", description = "Endpoints de gererenciamento de itens de cardapio")
@RestController
@RequestMapping("api/itens")
public class ItemDeCardapioController {

    @Autowired
    private ItensService itensService;

    @Operation(summary = "Adicionar Item no cardapio", description = "Cadastra um novo item")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarItensDto>> criarItem(@RequestBody @Valid CadastrarItensDto itemDto) {
        try {
            ListarItensDto savedItem = itensService.salvar(itemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(savedItem));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Listar itens de cardapio", description = "Lista todos os itens no cardapio")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ListarItensDto>>> listarItens() {
        List<ListarItensDto> itens = itensService.listarItens();
        return ResponseEntity.ok(new ApiResponse<>(itens));
    }

    @Operation(summary = "Listar item por id", description = "Obtem informações de um item do cardapio especifico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarItensDto>> obterItemPeloId(@PathVariable Long id) {
        ListarItensDto item = itensService.obterItemPeloId(id);
        return ResponseEntity.ok(new ApiResponse<>(item));
    }

    @Operation(summary = "Atualizar item", description = "Atualiza os dados de um item em especifico")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListarItensDto>> atualizarItens(@PathVariable Long id,
            @RequestBody @Valid AtualizarItemDto itemDto) {
        ListarItensDto item = itensService.atualizarItem(id, itemDto);
        return ResponseEntity.ok(new ApiResponse<>(item));
    }

    @Operation(summary = "Remover item de cardápio", description = "remove um item do cardápio pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removerItem(@PathVariable Long id) {
        itensService.removerItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>("Item do cardápio removido com sucesso"));
    }
}
