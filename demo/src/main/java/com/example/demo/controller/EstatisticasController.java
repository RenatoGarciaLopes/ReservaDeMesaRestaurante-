package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EstatisticasDto.EstatisticasGeraisDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasReservasDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasPedidosDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasFuncionariosDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasExportacaoCsvDto;
import com.example.demo.service.CsvService;
import com.example.demo.service.EstatisticasService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Estatísticas", description = "Endpoints para visualização de estatísticas do restaurante")
@RestController
@RequestMapping("api/estatisticas")
public class EstatisticasController {

    @Autowired
    private EstatisticasService estatisticasService;

    @Autowired
    private CsvService csvService;

    @Operation(summary = "Estatísticas Gerais", description = "Retorna estatísticas gerais do restaurante")
    @GetMapping("/gerais")
    public ResponseEntity<ApiResponse<EstatisticasGeraisDto>> obterEstatisticasGerais(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
        try {
            // Se não informadas, usar último mês
            if (dataInicio == null) {
                dataInicio = LocalDate.now().minusMonths(1);
            }
            if (dataFim == null) {
                dataFim = LocalDate.now();
            }

            EstatisticasGeraisDto estatisticas = estatisticasService.obterEstatisticasGerais(dataInicio, dataFim);
            return ResponseEntity.ok(new ApiResponse<>(estatisticas));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao obter estatísticas gerais", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Estatísticas de Reservas", description = "Retorna estatísticas detalhadas de reservas")
    @GetMapping("/reservas")
    public ResponseEntity<ApiResponse<EstatisticasReservasDto>> obterEstatisticasReservas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
        try {
            // Se não informadas, usar último mês
            if (dataInicio == null) {
                dataInicio = LocalDate.now().minusMonths(1);
            }
            if (dataFim == null) {
                dataFim = LocalDate.now();
            }

            EstatisticasReservasDto estatisticas = estatisticasService.obterEstatisticasReservas(dataInicio, dataFim);
            return ResponseEntity.ok(new ApiResponse<>(estatisticas));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao obter estatísticas de reservas", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Estatísticas de Pedidos", description = "Retorna estatísticas detalhadas de pedidos")
    @GetMapping("/pedidos")
    public ResponseEntity<ApiResponse<EstatisticasPedidosDto>> obterEstatisticasPedidos(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
        try {
            // Se não informadas, usar último mês
            if (dataInicio == null) {
                dataInicio = LocalDate.now().minusMonths(1);
            }
            if (dataFim == null) {
                dataFim = LocalDate.now();
            }

            EstatisticasPedidosDto estatisticas = estatisticasService.obterEstatisticasPedidos(dataInicio, dataFim);
            return ResponseEntity.ok(new ApiResponse<>(estatisticas));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao obter estatísticas de pedidos", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Estatísticas de Funcionários", description = "Retorna estatísticas de performance dos funcionários")
    @GetMapping("/funcionarios")
    public ResponseEntity<ApiResponse<EstatisticasFuncionariosDto>> obterEstatisticasFuncionarios(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
        try {
            // Se não informadas, usar último mês
            if (dataInicio == null) {
                dataInicio = LocalDate.now().minusMonths(1);
            }
            if (dataFim == null) {
                dataFim = LocalDate.now();
            }

            EstatisticasFuncionariosDto estatisticas = estatisticasService.obterEstatisticasFuncionarios(dataInicio, dataFim);
            return ResponseEntity.ok(new ApiResponse<>(estatisticas));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro ao obter estatísticas de funcionários", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Exportar estatísticas em CSV", description = "Exporta estatísticas completas para arquivo CSV")
    @GetMapping("/exportar")
    public ResponseEntity<Void> exportarEstatisticasCSV(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
            HttpServletResponse response)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        
        try {
            // Se não informadas, usar último mês
            if (dataInicio == null) {
                dataInicio = LocalDate.now().minusMonths(1);
            }
            if (dataFim == null) {
                dataFim = LocalDate.now();
            }

            String fileName = String.format("estatisticas-restaurante_%s_a_%s.csv", 
                    dataInicio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    dataFim.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            
            List<EstatisticasExportacaoCsvDto> dados = estatisticasService.converteEstatisticasCsv(dataInicio, dataFim);
            
            csvService.exportCsv(fileName, response, dados, EstatisticasExportacaoCsvDto.class);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao exportar estatísticas", e);
        }
    }
} 