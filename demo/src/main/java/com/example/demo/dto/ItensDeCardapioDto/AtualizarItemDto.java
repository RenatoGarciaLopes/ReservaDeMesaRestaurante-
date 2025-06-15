package com.example.demo.dto.ItensDeCardapioDto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class AtualizarItemDto {

    private String nome;

    private String descricao;

    @Positive(message = "O preço do produto deve ser positivo")
    private BigDecimal preco;

    private String imagemUrl;

    private Long categoriaId;
}
