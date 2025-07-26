package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ItensDeCardapioDto.AtualizarItemDto;
import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.service.ItensService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Tag(name = "Itens de cardapio", description = "Endpoints de gererenciamento de itens de cardapio")
@RestController
@RequestMapping("api/itens")
public class ItemDeCardapioController {

    @Autowired
    private ItensService itensService;

    @Operation(summary = "Servir imagem", description = "Serve uma imagem específica do item")
    @GetMapping("/imagem/{nomeArquivo}")
    public ResponseEntity<Resource> servirImagem(@PathVariable String nomeArquivo) {
        try {
            Path caminhoArquivo = Paths.get("uploads/imagens/" + nomeArquivo);
            Resource resource = new UrlResource(caminhoArquivo.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
    public ResponseEntity<ApiResponse<Page<ListarItensDto>>> listarItens(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {
                
        Page<ListarItensDto> itens = itensService.listarItens(pagina, tamanho, nome, categoriaId, status);
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

    @Operation(summary = "Inativar item de cardápio", description = "Inativa um item do cardápio pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> inativarItem(@PathVariable Long id) {
        itensService.inativarItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>("Item do cardápio inativado com sucesso"));
    }

    @Operation(summary = "Reativar item de cardápio", description = "Reativa um item do cardápio pelo ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> reativarItem(@PathVariable Long id) {
        itensService.reativarItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>("Item do cardápio inativado com sucesso"));
    }

    @Operation(
        summary = "Upload de imagem para item de cardápio", 
        description = "Faz o upload de uma imagem para um item de cardápio e retorna a URL da imagem."
    )
    @PostMapping(value = "/upload-imagem/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImagem(
        @PathVariable Long itemId,
        @Parameter(description = "Arquivo de imagem a ser enviado", required = true)
        @RequestPart("imagem") MultipartFile imagem) {
        try {
            String url = itensService.uploadImagem(imagem, itemId);
            return ResponseEntity.ok(new ApiResponse<>(url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(new ErrorResponse("Falha ao salvar imagem", e.getMessage())));
        }
    }
}
