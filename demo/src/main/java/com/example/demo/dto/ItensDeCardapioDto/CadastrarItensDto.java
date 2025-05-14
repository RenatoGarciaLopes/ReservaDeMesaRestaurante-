package com.example.demo.dto.ItensDeCardapioDto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CadastrarItensDto {

    @NotBlank(message = "O nome do item é obrigatorio")
    private String nome;

    private String descricao;

    @Positive(message = "O preço do produto deve ser positivo")
    @NotNull(message = "O preco do item é obrigatorio")
    private BigDecimal preco;

}
