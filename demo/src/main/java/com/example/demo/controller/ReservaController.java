package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.service.ReservaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Reservas", description = "Endpoints para gerenciamento de reservas")
@RestController
@RequestMapping("api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Registrar Reserva", description = "Registra uma nova reserva")
    @PostMapping
    public ResponseEntity<ApiResponse<ListarReservaDto>> criarReserva(@RequestBody @Valid CadastrarReservaDTO reservaDTO){
        try{
            ListarReservaDto savedReservaDto = reservaService.salvar(reservaDTO);
            ApiResponse<ListarReservaDto> response = new ApiResponse<>(savedReservaDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse("Erro interno",e.getMessage());
            ApiResponse<ListarReservaDto> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
