package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CadastrarMesaDto {

    @NotNull(message = "O número da mesa é obrigatório")
    @Positive(message = "O número da mesa deve ser positivo")
    private Integer numero;

    @NotNull(message = "A capacidade da mesa é obrigatório")
    @Min(value = 1, message = "A capacidade deve ser de pelo menos 1 pessoa")
    private Integer capacidade;
}
