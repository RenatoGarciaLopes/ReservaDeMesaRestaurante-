package com.example.demo.dto.FuncionarioDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.enums.Cargo;
import com.example.demo.service.Utils.FormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListarFuncionarioDto {

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Cargo cargo;
    private BigDecimal salario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataContratacao;

    public String getCpf() {
        return FormatUtils.formatarCpf(cpf);
    }

    public String getTelefone() {
        return FormatUtils.formatarTelefone(telefone);
    }
}