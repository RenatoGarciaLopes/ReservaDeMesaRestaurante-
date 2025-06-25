package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.enums.Cargo;
import com.example.demo.enums.StatusReserva;
import com.example.demo.service.Utils.FormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ListarReservaDto {

    private String nomeCliente;
    private String cpf;
    private String telefone;
    private Integer numeroMesa;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nomeFuncionario;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Cargo cargo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataReserva;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaReserva;
    private Integer quantidadePessoas;
    private StatusReserva status;

    public String getCpf() {
        return FormatUtils.formatarCpf(cpf);
    }

    public String getTelefone() {
        return FormatUtils.formatarTelefone(telefone);
    }
}
