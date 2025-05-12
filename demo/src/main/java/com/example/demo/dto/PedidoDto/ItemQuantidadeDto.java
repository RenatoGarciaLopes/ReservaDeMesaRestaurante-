package com.example.demo.dto.PedidoDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ItemQuantidadeDto {

    @NotNull(message = "ID do item do cardápio é obrigatório")
    private Long itemId;

    @NotNull(message = "Quantidade do item pedido é obrigatório")
    @Positive(message = "Quantidade deve ser maior que 0")
    private Integer quantidade;
}
