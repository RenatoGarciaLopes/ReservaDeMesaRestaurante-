package com.example.demo.dto.ClienteDto;

import java.time.LocalDateTime;

import com.example.demo.service.Utils.FormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListarClienteDto {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCadastro;
    private String observacoes;

    public String getCpf() {
        return FormatUtils.formatarCpf(cpf);
    }

    public String getTelefone() {
        return FormatUtils.formatarTelefone(telefone);
    }
}
