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

    @NotBlank(message = "O nome do item é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço do item é obrigatório")
    @Positive(message = "O preço do produto deve ser positivo")
    private BigDecimal preco;

    private String imagemUrl;

    @NotNull(message = "A categoria do item é obrigatório")
    private Long categoriaId;
}
