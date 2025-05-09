package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class ListarItensDto {

    private String nome;
    private String descricao;
    private BigDecimal preco;

}
