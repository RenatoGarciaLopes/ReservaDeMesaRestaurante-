package com.example.demo.dto.ClienteDto;

import java.time.LocalDateTime;

import com.example.demo.service.Utils.FormatUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListarClienteDto {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDateTime dataCadastro;
    private String observacoes;

    public String getCpf() {
        return FormatUtils.formatarCpf(cpf);
    }

    public String getTelefone() {
        return FormatUtils.formatarTelefone(telefone);
    }
}
