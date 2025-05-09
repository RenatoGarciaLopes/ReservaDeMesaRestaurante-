package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CadastrarItensDto {

    @NotNull(message = "O nome do item é obrigatorio")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço do item é obrigatorio")
    @Positive(message = "O preço do produto deve ser positivo")
    private BigDecimal preco;

}
