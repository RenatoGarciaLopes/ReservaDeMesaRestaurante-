package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.service.ItensService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Itens de cardapio", description = "Endpoints de gererenciamento de itens de cardapio" )
@RestController
@RequestMapping("api/itens")
public class ItemDeCardapioController {

    @Autowired
    private ItensService itensService;


    @Operation(summary = "Registrar Item no cardapio", description = "Cadastra um novo item")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarItensDto>> criarItem(@RequestBody @Valid CadastrarItensDto itemDto){
        try {
            ListarItensDto savedItem = itensService.salvar(itemDto);
            ApiResponse<ListarItensDto> response = new ApiResponse<>(savedItem);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ListarItensDto> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    

}
